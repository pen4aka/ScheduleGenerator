package com.example.ScheduleGenerator.dto;



import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
public class SubjectScheduleInfoDto {
    private Long id;
    private Long subjectId;
    private SubjectType type;
    private int totalHours;
    private int durationPerSession;
    private int weeksFrequency;
    private int totalWeeks;

}
