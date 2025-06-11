package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
@Entity
public class SubjectScheduleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    private int totalHours;
    private int durationPerSession;
    private int weeksFrequency;
    private int totalWeeks;

    @ManyToOne
    private Subject subject;

}
