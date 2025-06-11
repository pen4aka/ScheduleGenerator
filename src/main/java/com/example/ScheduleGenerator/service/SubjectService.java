package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private SemesterRepository semesterRepo;

    public SubjectDto create(SubjectDto dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setSemester(semesterRepo.findById(dto.getSemesterId()).orElseThrow());
        subject = subjectRepo.save(subject);
        dto.setId(subject.getId());
        return dto;
    }

    public List<SubjectDto> getAll() {
        return subjectRepo.findAll().stream().map(subject -> {
            SubjectDto dto = new SubjectDto();
            dto.setId(subject.getId());
            dto.setName(subject.getName());
            dto.setSemesterId(subject.getSemester().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}