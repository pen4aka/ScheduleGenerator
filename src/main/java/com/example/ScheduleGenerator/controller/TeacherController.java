package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.service.SubjectService;
import com.example.ScheduleGenerator.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final SubjectService subjectService;

    public TeacherController(
            TeacherService teacherService,
            SubjectService subjectService
    ) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto dto) {
        TeacherDto created = teacherService.createTeacher(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        TeacherDto dto = teacherService.getTeacher(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        List<TeacherDto> list = teacherService.getAllTeachers();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(
            @PathVariable Long id,
            @RequestBody TeacherDto dto) {
        TeacherDto updated = teacherService.updateTeacher(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}

