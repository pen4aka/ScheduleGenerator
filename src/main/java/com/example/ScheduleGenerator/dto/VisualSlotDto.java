package com.example.ScheduleGenerator.dto;

import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class VisualSlotDto {
    private DayOfWeek day;
    private LocalTime startTime;
    private int durationMinutes;

    private String subjectName;
    private SubjectType type;
    private String teacherName;
    private String roomName;
    private List<String> groupNames;
}
