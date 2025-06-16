package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.models.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public TeacherDto toDto(Teacher t) {
        TeacherDto dto = new TeacherDto();
        dto.setId(t.getId());
        dto.setName(t.getName());
        return dto;
    }

    public Teacher toEntity(TeacherDto dto) {
        Teacher t = new Teacher();
        t.setId(dto.getId());
        t.setName(dto.getName());
        return t;
    }
}
