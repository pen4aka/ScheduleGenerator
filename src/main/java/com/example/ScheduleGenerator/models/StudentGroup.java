package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_group")
    private Long nameGroup;

    @Column(name = "student_count")
    private int studentCount;
}