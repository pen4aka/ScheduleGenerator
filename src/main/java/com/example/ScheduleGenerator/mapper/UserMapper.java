package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.UserDto;
import com.example.ScheduleGenerator.models.User;
import com.example.ScheduleGenerator.models.enums.Role;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
