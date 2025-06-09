package com.example.ScheduleGenerator.models;


import com.example.ScheduleGenerator.models.enums.SubjectType;
import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private SubjectType subjectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

}