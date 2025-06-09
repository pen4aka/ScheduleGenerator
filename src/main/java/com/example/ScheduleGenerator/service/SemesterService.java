package com.example.ScheduleGenerator.service;



import com.example.ScheduleGenerator.dto.SemesterDto;
import com.example.ScheduleGenerator.mapper.SemesterMapper;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Transactional
    public SemesterDto create(SemesterDto dto) {
        Semester entity = SemesterMapper.toEntity(dto);
        Semester saved = semesterRepository.save(entity);
        return SemesterMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public SemesterDto get(Long id) {
        Semester s = semesterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found with id " + id));
        return SemesterMapper.toDto(s);
    }

    @Transactional(readOnly = true)
    public List<SemesterDto> getAll() {
        return semesterRepository.findAll().stream()
                .map(SemesterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SemesterDto update(Long id, SemesterDto dto) {
        Semester s = semesterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found with id " + id));
        s.setSemesterNo(dto.getSemesterNo());
        s.setDescription(dto.getDescription());
        Semester updated = semesterRepository.save(s);
        return SemesterMapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        semesterRepository.deleteById(id);
    }
}
