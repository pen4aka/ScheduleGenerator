package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.TeachingAssignmentDto;
import com.example.ScheduleGenerator.service.TeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class TeachingAssignmentController {
    @Autowired
    TeachingAssignmentService service;

    @PostMapping
    public ResponseEntity<TeachingAssignmentDto> assign(@RequestBody TeachingAssignmentDto dto) {
        return ResponseEntity.ok(service.assignTeacherToSubjectType(dto));
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<List<TeachingAssignmentDto>> getBySubject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAssignmentsBySubject(id));
    }
}
