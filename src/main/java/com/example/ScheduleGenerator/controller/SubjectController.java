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
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        SubjectDto created = subjectService.createSubject(subjectDto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SubjectDto> getSubject(@PathVariable("id") Long subjectId) {
        SubjectDto found = subjectService.getSubject(subjectId);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.listAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubjectDto> updateSubject(
            @PathVariable("id") Long subjectId,
            @RequestBody SubjectDto subjectDto
    ) {
        SubjectDto updated = subjectService.updateSubject(subjectId, subjectDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.noContent().build();
    }
}