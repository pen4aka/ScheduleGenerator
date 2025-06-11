package com.example.ScheduleGenerator.repository;


import com.example.ScheduleGenerator.models.ScheduledSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledSlotRepository extends JpaRepository<ScheduledSlot, Long> {
}
