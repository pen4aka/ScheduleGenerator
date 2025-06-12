package com.example.ScheduleGenerator.config;

import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.models.enums.Role;
import com.example.ScheduleGenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {
    @Autowired private UserRepository users;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (users.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            users.save(admin);
        }
    }
}