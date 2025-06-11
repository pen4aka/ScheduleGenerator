package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.VisualSlotDto;
import com.example.ScheduleGenerator.models.ScheduledSlot;

import java.util.stream.Collectors;

public class VisualSlotMapper {

    public static VisualSlotDto toDTO(ScheduledSlot slot) {
        VisualSlotDto dto = new VisualSlotDto();
        dto.setDay(slot.getDay());
        dto.setStartTime(slot.getStartTime());
        dto.setDurationMinutes(slot.getDurationMinutes());

        dto.setSubjectName(slot.getSubject().getName());
        dto.setType(slot.getType());
        dto.setTeacherName(slot.getTeacher().getName());
        dto.setRoomName(slot.getRoom().getName());

        dto.setGroupNames(
                slot.getGroups().stream()
                        .map(g -> g.getNameGroup().toString())
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
