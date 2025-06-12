package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.UserDto;
import com.example.ScheduleGenerator.mapper.UserMapper;
import com.example.ScheduleGenerator.models.AppUser;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto createUser(String username, String rawPassword) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(rawPassword));
        appUser.setRole(Role.USER);
        AppUser saved = userRepository.save(appUser);
        return UserMapper.toDto(saved);
    }

    @Transactional
    public UserDto promoteToAdmin(Long userId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        appUser.setRole(Role.ADMIN);
        return UserMapper.toDto(userRepository.save(appUser));
    }
}
