package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;

public class SubjectMapper {
    public static SubjectDto toDto(Subject s) {
        if (s == null) return null;
        SubjectDto d = new SubjectDto();
        d.setId(s.getId());
        d.setName(s.getName());
        d.setSemesterId(s.getSemester().getId());
        d.setSubjectType(s.getSubjectType());
        return d;
    }
    public static Subject toEntity(SubjectDto d, Semester sem) {
        if (d == null) return null;
        Subject s = new Subject();
        s.setId(d.getId());
        s.setName(d.getName());
        s.setSemester(sem);
        s.setSubjectType(d.getSubjectType());
        return s;
    }
}