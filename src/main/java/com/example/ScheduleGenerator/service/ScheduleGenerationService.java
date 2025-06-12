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
    @Autowired private ScheduledSlotRepository slotRepo;

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

        Set<String> scheduledKeys = new HashSet<>();
        List<SubjectScheduleInfo> subjectSemesterInfo = scheduleInfoRepo.findBySubject_Semester_Id(semesterId);
        for (SubjectScheduleInfo info : subjectSemesterInfo) {
            if (info.getTotalHours() <= 0) continue;
            Subject subject = info.getSubject();
            SubjectType type = info.getType();

            Optional<Teacher> maybeTeacher = assignments.stream()
                    .filter(a -> a.getSubject().equals(subject) && a.getType()==type)
                    .map(TeachingAssignment::getTeacher)
                    .findFirst();
            if (maybeTeacher.isEmpty()) continue;
            Teacher teacher = maybeTeacher.get();

            List<List<StudentGroup>> batches = optimizedGroupBatches(allGroups, type, rooms);

            for (List<StudentGroup> batch : batches) {
                String batchKey = subject.getId() + "|" + type + "|" +
                        batch.stream().map(StudentGroup::getId).sorted()
                                .map(Object::toString).collect(Collectors.joining(","));

                if (scheduledKeys.contains(batchKey)) {
                    continue;
                }

                findAvailableSlot(teacher, batch, rooms, type)
                        .ifPresent(slot -> {
                            slot.setSubject(subject);
                            slot.setType(type);
                            slot.setGroups(new ArrayList<>(batch));
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

    private Optional<ScheduledSlot> findAvailableSlot(
            Teacher teacher,
            List<StudentGroup> groups,
            List<Room> rooms,
            SubjectType type
    ) {
        int duration = (type == SubjectType.ЛАБОРАТОРНИ) ? 180 : 120;
        var existing = scheduledSlotRepo.findAll();

        List<DayOfWeek> loadBalancedDays = WORK_DAYS.stream()
                .sorted(Comparator.comparingInt(d ->
                        (int) scheduledSlotRepo.findAll().stream()
                                .filter(s -> s.getDay() == d)
                                .count()
                ))
                .collect(Collectors.toList());

        for (DayOfWeek day : loadBalancedDays) {
            for (LocalTime start : SLOT_START_TIMES) {
                LocalTime end = start.plusMinutes(duration);

                for (Room room : rooms) {
                    if (room.getType() != type) continue;

                    boolean conflict = hasConflict(day, start, duration, room, teacher, groups);
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

    private boolean hasConflict(
            DayOfWeek day, LocalTime start, int duration, Room room,
            Teacher teacher, List<StudentGroup> groups
    ) {
        LocalTime end = start.plusMinutes(duration);

        return scheduledSlotRepo.findAll().stream().anyMatch(slot -> {
            if (!slot.getDay().equals(day)) return false;

            LocalTime existingStart = slot.getStartTime();
            LocalTime existingEnd = existingStart.plusMinutes(slot.getDurationMinutes());

            boolean overlap = !(end.isBefore(existingStart) || start.isAfter(existingEnd));
            boolean roomConflict = slot.getRoom().equals(room);
            boolean teacherConflict = slot.getTeacher().equals(teacher);
            boolean groupConflict = !Collections.disjoint(slot.getGroups(), groups);

            return overlap && (roomConflict || teacherConflict || groupConflict);
        });
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

    private List<List<StudentGroup>> optimizedGroupBatches(
            List<StudentGroup> allGroups, SubjectType type, List<Room> rooms
    ) {
        List<List<StudentGroup>> result = new ArrayList<>();
        int totalStudents = allGroups.stream().mapToInt(StudentGroup::getStudentCount).sum();

        List<Room> validRooms = rooms.stream()
                .filter(r -> r.getType() == type)
                .sorted(Comparator.comparingInt(Room::getCapacity).reversed())
                .collect(Collectors.toList());

        if (validRooms.isEmpty()) {
            return allGroups.stream().map(List::of).collect(Collectors.toList());
        }

        Room maxRoom = validRooms.get(0);

        if (maxRoom.getCapacity() >= totalStudents) {
            result.add(allGroups);
        } else {
            List<StudentGroup> current = new ArrayList<>();
            int currentTotal = 0;

            for (StudentGroup group : allGroups) {
                if (currentTotal + group.getStudentCount() <= maxRoom.getCapacity()) {
                    current.add(group);
                    currentTotal += group.getStudentCount();
                } else {
                    result.add(new ArrayList<>(current));
                    current.clear();
                    current.add(group);
                    currentTotal = group.getStudentCount();
                }
            }
            if (!current.isEmpty()) result.add(current);
        }

        return result;
    }

    public void wipeScheduleData() {
        scheduledSlotRepo.deleteAll();
    }
}