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
        if (dto == null) throw new IllegalArgumentException("Body cannot be null");
        dto.setId(null);
        Semester entity = SemesterMapper.toEntity(dto);
        Semester saved  = semesterRepository.save(entity);
        return SemesterMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SemesterDto> getAll() {
        return semesterRepository.findAll().stream()
                .map(SemesterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        semesterRepository.deleteById(id);
    }
}
