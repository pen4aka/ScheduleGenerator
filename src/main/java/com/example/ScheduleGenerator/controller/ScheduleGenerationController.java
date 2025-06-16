package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.VisualSlotDto;
import com.example.ScheduleGenerator.models.enums.Season;
import com.example.ScheduleGenerator.service.ScheduleGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleGenerationController {

        @Autowired
        private ScheduleGenerationService scheduleGenerationService;

    @PostMapping("/generate/by-season")
    public ResponseEntity<String> generateBySeason(@RequestParam Season season) {
        scheduleGenerationService.generateScheduleForSeason(season);
        return ResponseEntity.ok("Generated schedules for all 4 “"
                + season + "” semesters.");
    }

    @GetMapping("/view")
    public ResponseEntity<List<VisualSlotDto>> view(@RequestParam Long semesterId) {
        List<VisualSlotDto> schedule = scheduleGenerationService.view(semesterId);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/wipe")
    public ResponseEntity<Void> wipeSchedule() {
        scheduleGenerationService.wipeScheduleData();
        return ResponseEntity.ok().build();
    }
}
