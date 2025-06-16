package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.SemesterDto;

import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.enums.Season;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {
    @Autowired private SemesterRepository semesterRepo;

    public SemesterDto create(SemesterDto dto) {
        Semester semester = new Semester();
        semester.setSemesterNo(dto.getSemesterNo());
        semester = semesterRepo.save(semester);
        dto.setId(semester.getId());
        return dto;
    }

    public List<SemesterDto> getAll() {
        return semesterRepo.findAll().stream().map(s -> {
            SemesterDto dto = new SemesterDto();
            dto.setId(s.getId());
            dto.setSemesterNo(s.getSemesterNo());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<Semester> getSemestersBySeason(Season season) {
        return semesterRepo.findAll().stream()
                .filter(s -> s.getSeason() == season)
                .collect(Collectors.toList());
    }
}
