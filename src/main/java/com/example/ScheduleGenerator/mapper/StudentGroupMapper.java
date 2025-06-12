package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.StudentGroupDto;
import com.example.ScheduleGenerator.models.StudentGroup;
import org.springframework.stereotype.Component;

@Component
public class StudentGroupMapper {

    private StudentGroupMapper() {
    }

    public static StudentGroupDto toDto(StudentGroup group) {
        if (group == null) {
            return null;
        }
        StudentGroupDto dto = new StudentGroupDto();
        dto.setId(group.getId());
        dto.setNameGroup(group.getNameGroup());
        dto.setStudentCount(group.getStudentCount());
        return dto;
    }

    public static StudentGroup toEntity(StudentGroupDto dto) {
        if (dto == null) {
            return null;
        }
        StudentGroup group = new StudentGroup();
        group.setId(dto.getId());
        group.setNameGroup(dto.getNameGroup());
        group.setStudentCount(dto.getStudentCount());
        return group;
    }
}