package com.example.ScheduleGenerator.dto;

import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;
    private SubjectType type;
}