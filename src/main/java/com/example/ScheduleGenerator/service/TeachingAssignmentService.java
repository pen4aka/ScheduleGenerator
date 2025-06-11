package com.example.ScheduleGenerator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ScheduleGenerator.dto.TeachingAssignmentDto;
import com.example.ScheduleGenerator.mapper.TeachingAssignmentMapper;
import com.example.ScheduleGenerator.repository.TeachingAssignmentRepository;

@Service
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository repo;
    private final TeachingAssignmentMapper mapper;

    @Autowired
    public TeachingAssignmentService(
            TeachingAssignmentRepository repo,
            TeachingAssignmentMapper mapper
    ) {
        this.repo   = repo;
        this.mapper = mapper;
    }


    public TeachingAssignmentDto assignTeacherToSubjectType(TeachingAssignmentDto dto) {
        var entity = mapper.toEntity(dto);
        var saved = repo.save(entity);
        return mapper.toDto(saved);
    }
    public List<TeachingAssignmentDto> getAssignmentsBySubject(Long subjectId) {
        return repo.findBySubjectId(subjectId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
