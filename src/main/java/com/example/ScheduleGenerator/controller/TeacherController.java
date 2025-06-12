package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.TeacherDto;
import com.example.ScheduleGenerator.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create")
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto dto) {
        return ResponseEntity.ok(teacherService.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TeacherDto>> getAll() {
        return ResponseEntity.ok(teacherService.getAll());
    }
}