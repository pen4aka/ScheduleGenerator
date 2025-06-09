package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.ScheduleDto;
import com.example.ScheduleGenerator.models.*;


public class ScheduleMapper {

    private ScheduleMapper() {
    }
    public static ScheduleDto toDto(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        ScheduleDto dto = new ScheduleDto();
        dto.setId(schedule.getId());

        if (schedule.getSubject() != null) {
            dto.setCourseId(schedule.getSubject().getId());
        }
        if (schedule.getTeacher() != null) {
            dto.setTeacherId(schedule.getTeacher().getId());
        }
        if (schedule.getRoom() != null) {
            dto.setRoomId(schedule.getRoom().getId());
        }
        if (schedule.getGroup() != null) {
            dto.setGroupId(schedule.getGroup().getId());
        }

        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setWeekParity(schedule.getWeekParity());

        return dto;
    }
    public static Schedule toEntity(
            ScheduleDto dto,
            Subject subject,
            Teacher teacher,
            Room room,
            StudentGroup group
    ) {
        if (dto == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);
        schedule.setRoom(room);
        schedule.setGroup(group);

        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setWeekParity(dto.getWeekParity());

        return schedule;
    }
}

