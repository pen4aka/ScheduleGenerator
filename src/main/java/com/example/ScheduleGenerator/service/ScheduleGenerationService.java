package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.VisualSlotDto;
import com.example.ScheduleGenerator.mapper.VisualSlotMapper;
import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.models.enums.Season;
import com.example.ScheduleGenerator.models.enums.SubjectType;
import com.example.ScheduleGenerator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleGenerationService {

    @Autowired private SemesterRepository semesterRepo;
    @Autowired private SubjectScheduleInfoRepository scheduleInfoRepo;
    @Autowired private TeachingAssignmentRepository teachingAssignmentRepo;
    @Autowired private RoomRepository roomRepo;
    @Autowired private StudentGroupRepository groupRepo;
    @Autowired private ScheduledSlotRepository scheduledSlotRepo;

    private static final List<DayOfWeek> WORK_DAYS = List.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    );

    private static final List<LocalTime> SLOT_START_TIMES = List.of(
            LocalTime.of(8, 0),
            LocalTime.of(10, 0),
            LocalTime.of(13, 0),
            LocalTime.of(15, 0),
            LocalTime.of(17, 0)
    );

    /**
     * Wrap in a transaction so partial writes on failure are rolled back.
     */
    @Transactional
    public void generateScheduleForSeason(Season season) {
        var sems = semesterRepo.findAll().stream()
                .filter(s -> s.getSeason() == season)
                .sorted(Comparator.comparing(Semester::getId))
                .toList();

        List<ScheduledSlot> globalExisting = scheduledSlotRepo.findAll().stream()
                     .filter(slot -> slot.getSemester().getSeason() == season)
                     .collect(Collectors.toCollection(ArrayList::new));

        var allAssign = teachingAssignmentRepo.findAll();
        var allRooms  = roomRepo.findAll();
        var allGroups = groupRepo.findAll();

        for (var sem : sems) {
            generateForOneSemester(
                    sem.getId(), allGroups, allRooms, allAssign, globalExisting
            );
        }
    }

    private void generateForOneSemester(
            Long semesterId,
            List<StudentGroup> allGroups,
            List<Room> allRooms,
            List<TeachingAssignment> allAssign,
            List<ScheduledSlot> globalExisting
    ) {
        var infos = scheduleInfoRepo.findBySubject_Semester_Id(semesterId);
        var scheduledKeys = new HashSet<String>();

        for (var info : infos) {
            if (info.getTotalHours() <= 0) continue;
            var subj  = info.getSubject();
            var type  = info.getType();

            var ta = allAssign.stream()
                    .filter(a -> a.getSubject().equals(subj) && a.getType() == type)
                    .findFirst().orElse(null);
            if (ta == null) continue;
            var teacher = ta.getTeacher();

            var batches = (type == SubjectType.ЛАБОРАТОРНИ)
                    ? optimizedGroupBatches(allGroups, allRooms)
                    : List.of(allGroups);

            for (var batch : batches) {
                var key = subj.getId() + "|" + type + "|" +
                        batch.stream().map(StudentGroup::getId).sorted()
                                .map(Object::toString).collect(Collectors.joining(","));
                if (!scheduledKeys.add(key)) continue;

                findSlot(semesterId, teacher, batch, allRooms, type, globalExisting)
                        .ifPresent(slot -> {
                            var sref = new Semester(); sref.setId(semesterId);
                            slot.setSemester(sref);
                            slot.setSubject(subj);
                            slot.setType(type);
                            slot.setGroups(new ArrayList<>(batch));
                            slot.setWeeksFrequency(info.getWeeksFrequency());
                            scheduledSlotRepo.save(slot);
                            globalExisting.add(slot);
                        });
            }
        }
    }

    private Optional<ScheduledSlot> findSlot(
            Long semesterId,
            Teacher teacher,
            List<StudentGroup> groups,
            List<Room> rooms,
            SubjectType type,
            List<ScheduledSlot> existing
    ) {
        int duration = (type == SubjectType.ЛАБОРАТОРНИ) ? 180 : 120;

        // Spread evenly across days
        var days = WORK_DAYS.stream()
                .sorted(Comparator.comparingInt(d ->
                        (int) existing.stream().filter(s -> s.getDay() == d).count()))
                .toList();

        for (var day : days) {
            for (var start : SLOT_START_TIMES) {
                var end = start.plusMinutes(duration);

                // primary rooms (exact type match)
                List<Room> primary = rooms.stream()
                        .filter(r -> r.getType() == type)
                        .toList();

                // fallback rooms for seminars only: use lecture rooms if no seminar room free
                List<Room> fallback = List.of();
                if (type == SubjectType.СЕМИНАРНИ) {
                    fallback = rooms.stream()
                            .filter(r -> r.getType() == SubjectType.ЛЕКЦИИ)
                            .toList();
                }

                // try primary first, then fallback (for seminars)
                for (var candidateSet : List.of(primary, fallback)) {
                    for (var room : candidateSet) {
                        boolean conflict = existing.stream().anyMatch(slot -> {
                            if (slot.getDay() != day) return false;
                            var s2 = slot.getStartTime();
                            var e2 = s2.plusMinutes(slot.getDurationMinutes());
                            if (end.isBefore(s2) || start.isAfter(e2)) return false;
                            if (slot.getRoom().equals(room))     return true;
                            if (slot.getTeacher().equals(teacher)) return true;
                            if (slot.getSemester().getId().equals(semesterId)
                                    && !Collections.disjoint(slot.getGroups(), groups)) {
                                return true;
                            }
                            return false;
                        });
                        if (!conflict) {
                            var s = new ScheduledSlot();
                            s.setDay(day);
                            s.setStartTime(start);
                            s.setDurationMinutes(duration);
                            s.setRoom(room);
                            s.setTeacher(teacher);
                            return Optional.of(s);
                        }
                    }
                    // if user asked only for primary (i.e. non-seminar), don't loop fallback
                    if (type != SubjectType.СЕМИНАРНИ) break;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Split only labs into sized batches; lectures & seminars remain all-groups.
     */
    private List<List<StudentGroup>> optimizedGroupBatches(
            List<StudentGroup> allGroups,
            List<Room> rooms
    ) {
        // find largest lab room
        var labs = rooms.stream()
                .filter(r -> r.getType() == SubjectType.ЛАБОРАТОРНИ)
                .sorted(Comparator.comparingInt(Room::getCapacity).reversed())
                .toList();

        int total = allGroups.stream().mapToInt(StudentGroup::getStudentCount).sum();
        if (labs.isEmpty() || labs.get(0).getCapacity() >= total) {
            return List.of(allGroups);
        }

        var result = new ArrayList<List<StudentGroup>>();
        var cur = new ArrayList<StudentGroup>();
        int curTot = 0, maxCap = labs.get(0).getCapacity();

        for (var g : allGroups) {
            if (curTot + g.getStudentCount() <= maxCap) {
                cur.add(g);
                curTot += g.getStudentCount();
            } else {
                result.add(new ArrayList<>(cur));
                cur.clear();
                cur.add(g);
                curTot = g.getStudentCount();
            }
        }
        if (!cur.isEmpty()) result.add(cur);
        return result;
    }

    public void wipeScheduleData() {
        scheduledSlotRepo.deleteAll();
    }

    public List<VisualSlotDto> view(Long semesterId) {
        return scheduledSlotRepo.findBySubject_Semester_Id(semesterId).stream()
                .map(VisualSlotMapper::toDTO)
                .sorted(Comparator
                        .comparing(VisualSlotDto::getDay)
                        .thenComparing(VisualSlotDto::getStartTime))
                .collect(Collectors.toList());
    }
}