package com.example.ScheduleGenerator.models;

import com.example.ScheduleGenerator.models.enums.SubjectType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TeachingAssignment")
public class TeachingAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;
}