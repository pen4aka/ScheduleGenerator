package com.example.ScheduleGenerator.dto;

import lombok.Data;

@Data
public class TeacherDto {
    private Long id;
    private String name;
    private Long subjectId;  // ID на предмета

}
