package com.example.ScheduleGenerator.service;


import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.mapper.TeacherMapper;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.models.Teacher;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import com.example.ScheduleGenerator.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public TeacherDto createTeacher(TeacherDto dto) {
        // Зареждаме Course по ID от DTO
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Course not found with id " + dto.getSubjectId()));
        Teacher teacher = TeacherMapper.toEntity(dto, subject);
        Teacher saved = teacherRepository.save(teacher);
        return TeacherMapper.toDto(saved);
    }

    public TeacherDto getTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
        return TeacherMapper.toDto(teacher);
    }

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
    }

    public TeacherDto updateTeacher(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
        teacher.setName(dto.getName());
        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Course not found with id " + dto.getSubjectId()));
            teacher.setSubject(subject);
        }
        Teacher updated = teacherRepository.save(teacher);
        return TeacherMapper.toDto(updated);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}

