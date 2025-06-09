package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "schedules")
public class Schedule {

    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Връзки към другите таблици (всички са ManyToOne, тъй като едно занятие се свързва с един преподавател, предмет, зала и група)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private StudentGroup group;

    // Ден от седмицата (например "Monday", "Tuesday", ...)
    private String dayOfWeek;

    // Начален и краен час
    private LocalTime startTime;
    private LocalTime endTime;

    // Поле за четна/нечетна/всяка седмица
    private String weekParity;  // Примерни стойности: "odd", "even", "both"

}

