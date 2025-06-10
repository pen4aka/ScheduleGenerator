//package com.example.ScheduleGenerator.controller;
//
//import com.example.ScheduleGenerator.dto.LoginDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//
//    private final AuthenticationManager authManager;
//
//    public AuthController(AuthenticationManager authManager) {
//        this.authManager = authManager;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginRequest) {
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                );
//
//        Authentication auth = authManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        return ResponseEntity.ok("Login successful");
//    }
//}