package com.example.ScheduleGenerator.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDto {
    private Long id;
    private String name;
    private Long semesterId;
}