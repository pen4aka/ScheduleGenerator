package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.RegisterRequestDto;
import com.example.ScheduleGenerator.models.AppUser;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @PutMapping("/admin/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> promoteUser(@PathVariable String username) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.ADMIN);
        userRepo.save(user);
        return ResponseEntity.ok("User promoted");
    }
}