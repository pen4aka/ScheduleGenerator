package com.example.ScheduleGenerator.dto;

import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
public class TeachingAssignmentDto {
    private Long subjectId;
    private Long teacherId;
    private SubjectType type;
}
