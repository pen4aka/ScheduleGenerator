package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.LoginDto;
import com.example.ScheduleGenerator.dto.RegisterDto;
import com.example.ScheduleGenerator.models.User;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.repository.UserRepository;
import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserRepository users;
    @Autowired private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto req) {
        if (users.findByUsername(req.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username taken");
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        users.save(u);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            return ResponseEntity.ok("Logged in as " + auth.getName());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid creds");
        }
    }
}