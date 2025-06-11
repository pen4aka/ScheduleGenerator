package com.example.ScheduleGenerator.service;

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

    public void generateSchedule() {
        List<StudentGroup> groups = groupRepo.findAll();
        List<Room> rooms = roomRepo.findAll();
        List<SubjectScheduleInfo> scheduleInfos = scheduleInfoRepo.findAll();
        List<TeachingAssignment> assignments = teachingAssignmentRepo.findAll();

        for (SubjectScheduleInfo info : scheduleInfos) {
            int semesterNumber = info.getSubject().getSemester().getSemesterNo().contains("8") ? 8 : 1;
            int numSessions = calculateRequiredSessions(info.getTotalHours(), semesterNumber);
            Subject subject = info.getSubject();
            SubjectType type = info.getType();

            Teacher teacher = assignments.stream()
                    .filter(a -> a.getSubject().equals(subject) && a.getType().equals(type))
                    .map(TeachingAssignment::getTeacher)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No teacher for " + subject.getName() + " " + type));

            List<StudentGroup> attendingGroups = (type == SubjectType.ЛЕКЦИИ) ?
                    groups : splitGroups(groups);

            for (int i = 0; i < numSessions; i++) {
                Optional<ScheduledSlot> slotOpt = findAvailableSlot(teacher, attendingGroups, rooms, type, i);
                if (slotOpt.isPresent()) {
                    ScheduledSlot slot = slotOpt.get();
                    slot.setSubject(subject);
                    slot.setType(type);
                    scheduledSlotRepo.save(slot);
                } else {
                    System.out.println("⚠️ Could not find slot for: " + subject.getName() + " - " + type);
                }
            }
        }
    }

    private int calculateRequiredSessions(int totalHours, int semesterNumber) {
        if (semesterNumber == 8) {
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
        return groups; // Initially no subgrouping
    }

    private Optional<ScheduledSlot> findAvailableSlot(
            Teacher teacher,
            List<StudentGroup> groups,
            List<Room> allRooms,
            SubjectType type,
            int index
    ) {
        int duration = switch (type) {
            case ЛЕКЦИИ -> 120;
            case СЕМИНАРНИ -> 120;
            case ЛАБОРАТОРНИ -> 180;
        };

        List<ScheduledSlot> existing = scheduledSlotRepo.findAll();

        for (DayOfWeek day : WORK_DAYS) {
            for (LocalTime startTime : SLOT_START_TIMES) {
                for (Room room : allRooms) {
                    System.out.printf("🧪 Trying slot: %s %s in %s%n", day, startTime, room.getName());
                    if (!room.getType().equals(type)) continue;

                    boolean roomBusy = existing.stream().anyMatch(slot ->
                            slot.getRoom().equals(room) &&
                                    slot.getDay().equals(day) &&
                                    slot.getStartTime().equals(startTime)
                    );
                    if (roomBusy) continue;

                    boolean teacherBusy = existing.stream().anyMatch(slot ->
                            slot.getTeacher().equals(teacher) &&
                                    slot.getDay().equals(day) &&
                                    slot.getStartTime().equals(startTime)
                    );
                    if (teacherBusy) continue;

                    boolean groupBusy = groups.stream().anyMatch(group ->
                            existing.stream().anyMatch(slot ->
                                    slot.getGroups().contains(group) &&
                                            slot.getDay().equals(day) &&
                                            slot.getStartTime().equals(startTime)
                            )
                    );
                    if (groupBusy) continue;

                    ScheduledSlot slot = new ScheduledSlot();
                    slot.setDay(day);
                    slot.setStartTime(startTime);
                    slot.setDurationMinutes(duration);
                    slot.setRoom(room);
                    slot.setTeacher(teacher);
                    slot.setGroups(groups);

                    return Optional.of(slot);
                }
            }
        }
        return Optional.empty();
    }
}