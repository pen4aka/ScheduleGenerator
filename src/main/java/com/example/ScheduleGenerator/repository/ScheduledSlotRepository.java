package com.example.ScheduleGenerator.repository;


import com.example.ScheduleGenerator.models.ScheduledSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScheduledSlotRepository extends JpaRepository<ScheduledSlot, Long>{
    List<ScheduledSlot> findBySubject_Semester_Id(Long semesterId);
    List<ScheduledSlot> findBySemester_Id(Long semesterId);
}
