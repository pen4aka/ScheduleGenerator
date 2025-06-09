package com.example.ScheduleGenerator.service;


import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.mapper.SubjectMapper;
import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import org.springframework.stereotype.Service;


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

    public SubjectDto createSubject(SubjectDto d) {
        Semester sem = semesterRepository.findById(d.getSemesterId()).orElseThrow();
        return SubjectMapper.toDto(subjectRepository.save(SubjectMapper.toEntity(d, sem)));
    }

    public SubjectDto getSubject(Long id) {
        return SubjectMapper.toDto(subjectRepository.findById(id).orElseThrow());
    }

    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream().map(SubjectMapper::toDto).collect(Collectors.toList());
    }

    public SubjectDto updateSubject(Long id, SubjectDto d) {
        Subject s = subjectRepository.findById(id).orElseThrow();
        s.setName(d.getName());
        if (d.getSemesterId() != null) {
            s.setSemester(semesterRepository.findById(d.getSemesterId()).orElseThrow());
        }
        return SubjectMapper.toDto(subjectRepository.save(s));
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}


