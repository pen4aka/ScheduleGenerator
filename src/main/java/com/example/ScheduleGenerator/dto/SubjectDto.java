package com.example.ScheduleGenerator.dto;


import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
public class SubjectDto {
    private Long id;
    private String name;
    private Long semesterId;
    private SubjectType subjectType;
}