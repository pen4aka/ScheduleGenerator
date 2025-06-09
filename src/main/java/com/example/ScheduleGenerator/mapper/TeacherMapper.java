package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.models.Teacher;

public class TeacherMapper {

    // Utility клас – не е нужно да се инстанцира
    private TeacherMapper() {
    }

    // --------------------
    // Entity -> DTO
    // --------------------
    public static TeacherDto toDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        if (teacher.getSubject() != null) {
            dto.setSubjectId(teacher.getSubject().getId());
        }
        return dto;
    }

    // --------------------
    // DTO -> Entity
    // --------------------
    // Тук приемаме вече зареден Course от база (или null, ако няма courseId).
    public static Teacher toEntity(TeacherDto dto, Subject subject) {
        if (dto == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());   // ако позволявате setId (или оставете null за autoincrement)
        teacher.setName(dto.getName());
        teacher.setSubject(subject);    // вече зареден от repository
        return teacher;
    }
}

