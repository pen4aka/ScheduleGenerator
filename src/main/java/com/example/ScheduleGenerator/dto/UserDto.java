package com.example.ScheduleGenerator.dto;

import com.example.ScheduleGenerator.models.enums.Role;
import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private String username;
    private Role role;
}
