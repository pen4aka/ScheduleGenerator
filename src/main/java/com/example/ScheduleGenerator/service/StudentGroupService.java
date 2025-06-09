package com.example.ScheduleGenerator.service;


import com.example.ScheduleGenerator.dto.StudentGroupDto;
import com.example.ScheduleGenerator.mapper.StudentGroupMapper;
import com.example.ScheduleGenerator.models.StudentGroup;
import com.example.ScheduleGenerator.repository.StudentGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentGroupService {

    private final StudentGroupRepository groupRepository;

    public StudentGroupService(StudentGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public StudentGroupDto createGroup(StudentGroupDto dto) {
        StudentGroup group = StudentGroupMapper.toEntity(dto);
        StudentGroup saved = groupRepository.save(group);
        return StudentGroupMapper.toDto(saved);
    }

    public StudentGroupDto getGroup(Long id) {
        StudentGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id " + id));
        return StudentGroupMapper.toDto(group);
    }

    public List<StudentGroupDto> getAllGroups() {
        List<StudentGroup> groups = groupRepository.findAll();
        return groups.stream()
                .map(StudentGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentGroupDto updateGroup(Long id, StudentGroupDto dto) {
        StudentGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id " + id));
        group.setNameGroup(dto.getNameGroup());
        group.setStudentCount(dto.getStudentCount());
        StudentGroup updated = groupRepository.save(group);
        return StudentGroupMapper.toDto(updated);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}

