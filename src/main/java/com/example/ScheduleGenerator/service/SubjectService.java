package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.mapper.SubjectMapper;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    public SubjectService(SubjectRepository subjectRepository, SemesterRepository semesterRepository) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
    }

    @Transactional
    public SubjectDto createSubject(SubjectDto subjectDto) {
        Semester semesterEntity = semesterRepository.findById(subjectDto.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found: " + subjectDto.getSemesterId()));
        Subject savedSubject = subjectRepository.save(
                SubjectMapper.toEntity(subjectDto, semesterEntity)
        );
        return SubjectMapper.toDto(savedSubject);
    }

    @Transactional(readOnly = true)
    public SubjectDto getSubject(Long subjectId) {
        Subject subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectId));
        return SubjectMapper.toDto(subjectEntity);
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> listAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(SubjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubjectDto updateSubject(Long subjectId, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectId));
        existingSubject.setName(subjectDto.getName());
        existingSubject.setSubjectType(subjectDto.getSubjectType());
        if (subjectDto.getSemesterId() != null) {
            Semester semesterEntity = semesterRepository.findById(subjectDto.getSemesterId())
                    .orElseThrow(() -> new RuntimeException("Semester not found: " + subjectDto.getSemesterId()));
            existingSubject.setSemester(semesterEntity);
        }
        Subject updatedSubject = subjectRepository.save(existingSubject);
        return SubjectMapper.toDto(updatedSubject);
    }

    @Transactional
    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}