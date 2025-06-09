package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "semester_no", nullable = false)
    private int semesterNo; // 1–8

    private String description; // напр. "1-ви семестър"
    // + getters/setters
}
