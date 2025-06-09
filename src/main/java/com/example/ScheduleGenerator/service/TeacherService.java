package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.models.Teacher;
import com.example.ScheduleGenerator.mapper.TeacherMapper;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import com.example.ScheduleGenerator.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TeacherDto createTeacher(TeacherDto dto) {
        Subject subj = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found: " + dto.getSubjectId()));
        Teacher saved = teacherRepository.save(
                TeacherMapper.toEntity(dto, subj) );
        return TeacherMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public TeacherDto getTeacher(Long teacherId) {
        Teacher teacherEntity = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));
        return TeacherMapper.toDto(teacherEntity);
    }

    @Transactional(readOnly = true)
    public List<TeacherDto> listAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Teacher existing = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));
        existing.setName(teacherDto.getName());
        if (teacherDto.getSubjectId() != null) {
            Subject subjectEntity = subjectRepository.findById(teacherDto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found: " + teacherDto.getSubjectId()));
            existing.setSubject(subjectEntity);
        }
        Teacher updated = teacherRepository.save(existing);
        return TeacherMapper.toDto(updated);
    }

    @Transactional
    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}