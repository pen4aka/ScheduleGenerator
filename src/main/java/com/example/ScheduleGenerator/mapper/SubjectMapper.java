package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;

public class SubjectMapper {
    private SubjectMapper() {}

    public static SubjectDto toDto(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectDto dto = new SubjectDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setSemesterId(subject.getSemester().getId());
        dto.setSubjectType(subject.getSubjectType());
        return dto;
    }

    public static Subject toEntity(SubjectDto dto, Semester semester) {
        if (dto == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setName(dto.getName());
        subject.setSemester(semester);
        subject.setSubjectType(dto.getSubjectType());
        return subject;
    }
}