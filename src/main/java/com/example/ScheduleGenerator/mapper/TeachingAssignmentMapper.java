package com.example.ScheduleGenerator.mapper;

import org.springframework.stereotype.Component;

import com.example.ScheduleGenerator.dto.TeachingAssignmentDto;
import com.example.ScheduleGenerator.models.TeachingAssignment;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.models.Teacher;

@Component
public class TeachingAssignmentMapper {

    public TeachingAssignmentDto toDto(TeachingAssignment ta) {
        if (ta == null) return null;
        TeachingAssignmentDto dto = new TeachingAssignmentDto();
        dto.setSubjectId(ta.getSubject().getId());
        dto.setTeacherId(ta.getTeacher().getId());
        dto.setType(ta.getType());
        return dto;
    }

    public TeachingAssignment toEntity(TeachingAssignmentDto dto) {
        if (dto == null) return null;
        TeachingAssignment ta = new TeachingAssignment();
        // set the enum
        ta.setType(dto.getType());
        // build minimal Subject proxy
        Subject subj = new Subject();
        subj.setId(dto.getSubjectId());
        ta.setSubject(subj);
        // build minimal Teacher proxy
        Teacher teach = new Teacher();
        teach.setId(dto.getTeacherId());
        ta.setTeacher(teach);
        return ta;
    }
}
