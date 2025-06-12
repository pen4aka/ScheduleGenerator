package com.example.ScheduleGenerator.models;

import jakarta.persistence.*;
import lombok.Data;

import com.example.ScheduleGenerator.models.enums.Role;

@Data
@Entity
@Table(name = "users")
public class AppUser {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String username;

        private String password;

        @Enumerated(EnumType.STRING)
        private Role role;
    }
