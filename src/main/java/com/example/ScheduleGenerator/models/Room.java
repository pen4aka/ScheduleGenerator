package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;
import com.example.ScheduleGenerator.models.enums.SubjectType;

@Data
@Entity

@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

    @Enumerated(EnumType.STRING)
    private SubjectType type;

}


