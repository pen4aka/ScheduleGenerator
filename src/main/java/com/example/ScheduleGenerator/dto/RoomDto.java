package com.example.ScheduleGenerator.dto;

import com.example.ScheduleGenerator.models.enums.SubjectType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;
    private SubjectType type;
}