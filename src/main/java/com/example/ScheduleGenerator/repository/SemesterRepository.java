package com.example.ScheduleGenerator.repository;

import com.example.ScheduleGenerator.models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {}