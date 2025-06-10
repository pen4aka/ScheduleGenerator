package com.example.ScheduleGenerator.config;

import com.example.ScheduleGenerator.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetails;

    public SecurityConfig(CustomUserDetailsService userDetails) {
        this.userDetails = userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Build AuthenticationManager from our UserDetailsService
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        auth
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF for simplicity in this API example
                .csrf(csrf -> csrf.disable())

                // authorize requests via the new lambda-based DSL
                .authorizeHttpRequests(auth -> auth

                        // 1) allow anyone to register or login under /users/**
                        .requestMatchers(HttpMethod.POST, "/users/register", "/users/login")
                        .permitAll()

                        // 2) require ADMIN for all other POSTs
                        .requestMatchers(HttpMethod.POST, "/**")
                        .hasRole("ADMIN")

                        // 3) USER or ADMIN can read schedules, generate, etc. via GET
                        .requestMatchers(HttpMethod.GET, "/generate/**", "/schedule/**")
                        .hasAnyRole("USER","ADMIN")

                        // 4) any other request just needs authentication
                        .anyRequest()
                        .authenticated()
                )

                // you can use HTTP Basic or switch to formLogin() if you prefer
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}