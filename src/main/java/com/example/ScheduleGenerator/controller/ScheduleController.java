package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.ScheduleDto;
import com.example.ScheduleGenerator.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(@RequestBody ScheduleDto dto) {
        ScheduleDto created = scheduleService.createSchedule(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getById(@PathVariable Long id) {
        ScheduleDto dto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAll() {
        List<ScheduleDto> list = scheduleService.getAllSchedules();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> update(
            @PathVariable Long id,
            @RequestBody ScheduleDto dto) {
        ScheduleDto updated = scheduleService.updateSchedule(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
