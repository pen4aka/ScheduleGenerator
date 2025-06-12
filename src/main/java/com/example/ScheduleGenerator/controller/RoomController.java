package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @PostMapping
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.create(dto));
    }
}
