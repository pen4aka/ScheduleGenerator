package com.example.ScheduleGenerator.repository;

import com.example.ScheduleGenerator.models.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, Long> {
    List<TeachingAssignment> findBySubjectId(Long subjectId);
}