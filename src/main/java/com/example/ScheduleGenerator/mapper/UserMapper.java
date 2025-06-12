package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.UserDto;
import com.example.ScheduleGenerator.models.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(AppUser appUser) {
        UserDto dto = new UserDto();
        dto.setId(appUser.getId());
        dto.setUsername(appUser.getUsername());
        dto.setRole(appUser.getRole());
        return dto;
    }
}
