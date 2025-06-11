package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/post/create")
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectDto dto) {
        return ResponseEntity.ok(subjectService.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAll());
    }

}