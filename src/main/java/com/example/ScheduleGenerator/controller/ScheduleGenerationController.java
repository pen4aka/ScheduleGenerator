package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.VisualSlotDto;
import com.example.ScheduleGenerator.service.ScheduleGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleGenerationController {

        @Autowired
        private ScheduleGenerationService service;

        @PostMapping("/generate")
        public ResponseEntity<Void> generate(@RequestParam Long semesterId) {
            service.wipeScheduleData();
            service.generateSchedule(semesterId);
            return ResponseEntity.ok().build();
        }

    @GetMapping("/view")
    public ResponseEntity<List<VisualSlotDto>> view(@RequestParam Long semesterId) {
        List<VisualSlotDto> schedule = service.getSchedule(semesterId);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/wipe")
    public ResponseEntity<Void> wipeSchedule() {
        service.wipeScheduleData();
        return ResponseEntity.ok().build();
    }
}
