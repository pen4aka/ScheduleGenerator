package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.UserDto;
import com.example.ScheduleGenerator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UserDto created = userService.createUser(username, password);
        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/{id}/make-admin")
    public ResponseEntity<UserDto> makeAdmin(@PathVariable("id") Long id) {
        UserDto updated = userService.promoteToAdmin(id);
        return ResponseEntity.ok(updated);
    }
}
