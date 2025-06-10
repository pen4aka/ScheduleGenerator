package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.models.Room;
import com.example.ScheduleGenerator.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody RoomDto roomDto) {
        try {
            RoomDto room = roomService.createRoom(roomDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(room);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Room already exists");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating room");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoomDto> getById(@PathVariable Long id) {
        RoomDto dto = roomService.getRoom(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RoomDto>> getAll() {
        List<RoomDto> list = roomService.getAllRooms();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(
            @PathVariable Long id,
            @RequestBody RoomDto dto) {
        RoomDto updated = roomService.updateRoom(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
