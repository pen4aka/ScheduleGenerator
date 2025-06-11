package com.example.ScheduleGenerator.models;


import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import com.example.ScheduleGenerator.models.enums.SubjectType;
import lombok.Data;

@Data
@Entity
public class ScheduledSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek day;

    private LocalTime startTime;

    private int durationMinutes;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private List<StudentGroup> groups;

    @ManyToOne
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private SubjectType type;
}
