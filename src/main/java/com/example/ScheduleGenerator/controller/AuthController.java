package com.example.ScheduleGenerator.controller;

import com.example.ScheduleGenerator.dto.LoginDto;
import com.example.ScheduleGenerator.jwtStrategy.JwtUtil;
import com.example.ScheduleGenerator.models.AppUser;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    @PutMapping("/admin/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> promoteUser(@PathVariable String username) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.ADMIN);
        userRepo.save(user);
        return ResponseEntity.ok("User promoted");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}