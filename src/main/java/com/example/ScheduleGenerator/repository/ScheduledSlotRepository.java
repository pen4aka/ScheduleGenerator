package com.example.ScheduleGenerator.repository;


import com.example.ScheduleGenerator.models.ScheduledSlot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledSlotRepository extends JpaRepository<ScheduledSlot, Long> {
    List<ScheduledSlot> findBySubject_Semester_Id(Long semesterId);

    void deleteBySubject_Semester_Id(Long semesterId);
}
