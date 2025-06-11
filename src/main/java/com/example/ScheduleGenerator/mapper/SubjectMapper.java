package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public SubjectDto toDto(Subject s) {
        SubjectDto dto = new SubjectDto();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setSemesterId(s.getSemester().getId());
        return dto;
    }

    public Subject toEntity(SubjectDto dto, Semester semester) {
        Subject s = new Subject();
        s.setId(dto.getId());
        s.setName(dto.getName());
        s.setSemester(semester);
        return s;
    }
}