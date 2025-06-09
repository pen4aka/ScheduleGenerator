package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.SubjectDto;
import com.example.ScheduleGenerator.service.SemesterService;
import com.example.ScheduleGenerator.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SemesterService semesterService;

    public SubjectController(SubjectService subjectService,SemesterService semesterService) {
        this.subjectService = subjectService;
        this.semesterService = semesterService;
    }

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectDto dto) {
        SubjectDto created = subjectService.createSubject(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getById(@PathVariable Long id) {
        SubjectDto dto = subjectService.getSubject(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAll() {
        List<SubjectDto> list = subjectService.getAllSubjects();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> update(
            @PathVariable Long id,
            @RequestBody SubjectDto dto) {
        SubjectDto updated = subjectService.updateSubject(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}

