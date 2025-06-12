package com.example.ScheduleGenerator.controller;


import com.example.ScheduleGenerator.dto.SemesterDto;
import com.example.ScheduleGenerator.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping("/create")
    public ResponseEntity<SemesterDto> create(@RequestBody SemesterDto dto) {
        return ResponseEntity.ok(semesterService.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SemesterDto>> getAll() {
        List<SemesterDto> list = semesterService.getAll();
        return ResponseEntity.ok(list);
    }
}

