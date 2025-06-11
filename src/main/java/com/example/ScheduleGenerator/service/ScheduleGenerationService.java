package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.VisualSlotDto;
import com.example.ScheduleGenerator.mapper.VisualSlotMapper;
import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import com.example.ScheduleGenerator.models.enums.SubjectType;


@Service
public class ScheduleGenerationService {

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

    public void generateSchedule(Long semesterId) {
        var allGroups   = groupRepo.findAll();
        var rooms       = roomRepo.findAll();
        var infos       = scheduleInfoRepo.findAll();
        var assignments = teachingAssignmentRepo.findAll();

        // A Set of keys we’ve already scheduled, to prevent duplicates
        Set<String> scheduledKeys = new HashSet<>();
        List<SubjectScheduleInfo> subjectSemesterInfo = scheduleInfoRepo.findBySubject_Semester_Id(semesterId);
        for (SubjectScheduleInfo info : infos) {
            if (info.getTotalHours() <= 0) continue;       // skip zeros
            Subject      subject = info.getSubject();
            SubjectType  type    = info.getType();

            // find teacher
            Optional<Teacher> maybeTeacher = assignments.stream()
                    .filter(a -> a.getSubject().equals(subject) && a.getType()==type)
                    .map(TeachingAssignment::getTeacher)
                    .findFirst();
            if (maybeTeacher.isEmpty()) continue;
            Teacher teacher = maybeTeacher.get();

            // build “batches” of groups
            List<List<StudentGroup>> batches = new ArrayList<>();
            if (type == SubjectType.ЛЕКЦИИ) {
                batches.add(allGroups);
            } else {
                allGroups.forEach(g -> batches.add(List.of(g)));
            }

            // for each batch, schedule exactly ONE slot
            for (List<StudentGroup> batch : batches) {
                // compose a unique key per-subject/type/batch
                String batchKey = subject.getId() + "|" + type + "|" +
                        batch.stream().map(StudentGroup::getId).sorted()
                                .map(Object::toString).collect(Collectors.joining(","));

                if (scheduledKeys.contains(batchKey)) {
                    continue;   // already scheduled exactly one
                }

                findAvailableSlot(teacher, batch, rooms, type)
                        .ifPresent(slot -> {
                            slot.setSubject(subject);
                            slot.setType(type);
                            slot.setGroups(new ArrayList<>(batch));
                            // how often it runs across the semester:
                            slot.setWeeksFrequency(info.getWeeksFrequency());
                            scheduledSlotRepo.save(slot);
                            scheduledKeys.add(batchKey);
                        });
            }
        }
    }
    public List<VisualSlotDto> getSchedule(Long semesterId) {
        return slotRepo.findBySubject_Semester_Id(semesterId).stream()
                .map(VisualSlotMapper::toDTO)
                .sorted(Comparator
                        .comparing(VisualSlotDto::getDay)
                        .thenComparing(VisualSlotDto::getStartTime))
                .collect(Collectors.toList());
    }

    // … calculateRequiredSessions, splitGroups unchanged …

    private Optional<ScheduledSlot> findAvailableSlot(
            Teacher teacher,
            List<StudentGroup> groups,
            List<Room> rooms,
            SubjectType type
    ) {
        int duration = (type == SubjectType.ЛАБОРАТОРНИ) ? 180 : 120;
        var existing = scheduledSlotRepo.findAll();

        for (DayOfWeek day : WORK_DAYS) {
            for (LocalTime start : SLOT_START_TIMES) {
                for (Room room : rooms) {
                    if (room.getType() != type) continue;

                    boolean conflict = existing.stream().anyMatch(s ->
                            s.getDay() == day &&
                                    s.getStartTime().equals(start) &&
                                    (s.getRoom().equals(room) ||
                                            s.getTeacher().equals(teacher) ||
                                            !Collections.disjoint(s.getGroups(), groups))
                    );
                    if (conflict) continue;

                    ScheduledSlot s = new ScheduledSlot();
                    s.setDay(day);
                    s.setStartTime(start);
                    s.setDurationMinutes(duration);
                    s.setRoom(room);
                    s.setTeacher(teacher);
                    return Optional.of(s);
                }
            }
        }
        return Optional.empty();
    }

    private int calculateRequiredSessions(int totalHours, int semNumber) {
        if (semNumber == 8) {
            if (totalHours == 20) return 10;
            if (totalHours == 10) return 5;
        }
        return switch (totalHours) {
            case 45 -> 15;
            case 30 -> 15;
            case 15 -> 8;
            default -> 0;
        };
    }

    private List<StudentGroup> splitGroups(List<StudentGroup> groups) {
        // Simple: return each group individually
        return new ArrayList<>(groups);
    }
    @Autowired
    private ScheduledSlotRepository slotRepo;

    public List<VisualSlotDto> getSchedule() {
        return slotRepo.findAll().stream()
                .map(VisualSlotMapper::toDTO)
                .sorted(Comparator
                        .comparing(VisualSlotDto::getDay)
                        .thenComparing(VisualSlotDto::getStartTime))
                .collect(Collectors.toList());
    }
}