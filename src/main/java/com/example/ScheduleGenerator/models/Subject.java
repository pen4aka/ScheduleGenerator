package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "subjects")
@ToString(exclude = {"semester"})
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Semester semester;

    @OneToMany(mappedBy = "subject")
    private List<TeachingAssignment> assignments;

}