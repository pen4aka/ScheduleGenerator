package com.example.ScheduleGenerator.repository;


import com.example.ScheduleGenerator.models.SubjectScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectScheduleInfoRepository extends JpaRepository<SubjectScheduleInfo, Long> {
}
