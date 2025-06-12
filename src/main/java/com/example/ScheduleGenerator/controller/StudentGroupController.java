package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.StudentGroupDto;
import com.example.ScheduleGenerator.service.StudentGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudentGroupController {

    private final StudentGroupService groupService;

    public StudentGroupController(StudentGroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<StudentGroupDto> create(@RequestBody StudentGroupDto dto) {
        StudentGroupDto created = groupService.createGroup(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StudentGroupDto>> getAll() {
        List<StudentGroupDto> list = groupService.getAllGroups();
        return ResponseEntity.ok(list);
    }
}
