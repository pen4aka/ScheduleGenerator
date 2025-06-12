package com.example.ScheduleGenerator.service;


import com.example.ScheduleGenerator.dto.StudentGroupDto;
import com.example.ScheduleGenerator.mapper.StudentGroupMapper;
import com.example.ScheduleGenerator.models.StudentGroup;
import com.example.ScheduleGenerator.repository.StudentGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentGroupService {

    private final StudentGroupRepository groupRepository;

    public StudentGroupService(StudentGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
    @Transactional
    public StudentGroupDto createGroup(StudentGroupDto dto) {
        StudentGroup group = StudentGroupMapper.toEntity(dto);
        StudentGroup saved = groupRepository.save(group);
        return StudentGroupMapper.toDto(saved);
    }
    @Transactional
    public List<StudentGroupDto> getAllGroups() {
        List<StudentGroup> groups = groupRepository.findAll();
        return groups.stream()
                .map(StudentGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}

