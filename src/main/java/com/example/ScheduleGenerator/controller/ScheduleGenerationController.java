package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.service.ScheduleGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleGenerationController {

        @Autowired
        private ScheduleGenerationService service;

        @PostMapping("/generate")
        public ResponseEntity<Void> generate() {
            service.generateSchedule();
            return ResponseEntity.ok().build();
        }
}
