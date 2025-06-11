package com.example.ScheduleGenerator.models;


import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @JoinTable(
            name = "scheduled_slot_groups",
            joinColumns = @JoinColumn(name = "scheduled_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<StudentGroup> groups = new ArrayList<>();

    @ManyToOne
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    private int weeksFrequency = 1;
}
