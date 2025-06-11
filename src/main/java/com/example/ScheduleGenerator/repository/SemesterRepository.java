package com.example.ScheduleGenerator.repository;

import com.example.ScheduleGenerator.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findBySemesterNo(String semesterNo);
}