package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.TeachingAssignmentDto;
import com.example.ScheduleGenerator.models.TeachingAssignment;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import com.example.ScheduleGenerator.repository.TeacherRepository;
import com.example.ScheduleGenerator.repository.TeachingAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingAssignmentService {
    @Autowired private TeachingAssignmentRepository repo;
    @Autowired private TeacherRepository teacherRepo;
    @Autowired private SubjectRepository subjectRepo;

    public TeachingAssignmentDto assignTeacherToSubjectType(TeachingAssignmentDto dto) {
        TeachingAssignment assignment = new TeachingAssignment();
        assignment.setType(dto.getType());
        assignment.setTeacher(teacherRepo.findById(dto.getTeacherId()).orElseThrow());
        assignment.setSubject(subjectRepo.findById(dto.getSubjectId()).orElseThrow());
        repo.save(assignment);
        return dto;
    }

    public List<TeachingAssignmentDto> getAssignmentsBySubject(Long subjectId) {
        return repo.findBySubjectId(subjectId).stream().map(a -> {
            TeachingAssignmentDto dto = new TeachingAssignmentDto();
            dto.setSubjectId(a.getSubject().getId());
            dto.setTeacherId(a.getTeacher().getId());
            dto.setType(a.getType());
            return dto;
        }).collect(Collectors.toList());
    }
}
