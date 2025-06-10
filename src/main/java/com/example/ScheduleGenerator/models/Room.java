package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

    @Column(name = "has_computers", nullable = false)
    private boolean hasComputers;

    @Column(name = "has_projector", nullable = false)
    private boolean hasProjector;

    // Свързване с графика – една зала може да се използва за много занятия
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

}


