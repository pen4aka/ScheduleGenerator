package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.models.Teacher;
import com.example.ScheduleGenerator.models.Subject;

public class TeacherMapper {
    private TeacherMapper() {}

    public static TeacherDto toDto(Teacher teacher) {
        if (teacher == null) return null;
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setSubjectId(teacher.getSubject().getId());  // <-- subject, not course
        return dto;
    }

    public static Teacher toEntity(TeacherDto dto, Subject subject) {
        if (dto == null) return null;
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setName(dto.getName());
        teacher.setSubject(subject);                     // <-- set subject
        return teacher;
    }
}
