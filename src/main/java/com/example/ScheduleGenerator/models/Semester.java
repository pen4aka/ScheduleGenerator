package com.example.ScheduleGenerator.models;

import com.example.ScheduleGenerator.models.enums.Season;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "semesters")
@ToString(exclude = "subjects")
public class Semester {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "semester_no", nullable = false)
    private String semesterNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();

}
