package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.LoginDto;
import com.example.ScheduleGenerator.dto.RegisterRequestDto;
import com.example.ScheduleGenerator.models.AppUser;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository users;
    @Autowired private PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(users.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto req) {
        if (users.findByUsername(req.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username taken");
        AppUser u = new AppUser();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        users.save(u);
        return ResponseEntity.ok("Registered");
    }
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        Optional<AppUser> user = users.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        users.delete(user.get());
        return ResponseEntity.ok("User deleted");
    }

}