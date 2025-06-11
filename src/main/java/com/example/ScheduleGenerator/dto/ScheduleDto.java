package com.example.ScheduleGenerator.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDto {

    private Long id;
    private Long courseId;
    private Long teacherId;
    private Long roomId;
    private Long groupId;

    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String weekParity; // "odd", "even", "both"

}

