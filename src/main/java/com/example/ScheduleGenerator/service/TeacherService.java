package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.models.Teacher;
import com.example.ScheduleGenerator.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    public TeacherDto create(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher = teacherRepo.save(teacher);
        dto.setId(teacher.getId());
        return dto;
    }

    public List<TeacherDto> getAll() {
        return teacherRepo.findAll().stream().map(t -> {
            TeacherDto dto = new TeacherDto();
            dto.setId(t.getId());
            dto.setName(t.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}