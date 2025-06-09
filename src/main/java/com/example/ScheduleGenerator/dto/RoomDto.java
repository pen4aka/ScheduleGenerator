package com.example.ScheduleGenerator.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;

    private Boolean hasComputers;
    private Boolean hasProjector;
}