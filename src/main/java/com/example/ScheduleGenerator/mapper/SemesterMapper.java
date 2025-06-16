package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.SemesterDto;
import com.example.ScheduleGenerator.models.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {
    public SemesterDto toDto(Semester s) {
        SemesterDto dto = new SemesterDto();
        dto.setId(s.getId());
        dto.setSemesterNo(s.getSemesterNo());
        return dto;
    }

    public Semester toEntity(SemesterDto dto) {
        Semester s = new Semester();
        s.setId(dto.getId());
        s.setSemesterNo(dto.getSemesterNo());
        return s;
    }
}