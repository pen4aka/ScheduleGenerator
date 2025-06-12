package com.example.ScheduleGenerator.repository;


import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.SubjectScheduleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectScheduleInfoRepository extends JpaRepository<SubjectScheduleInfo, Long> {
    List<SubjectScheduleInfo> findBySubject_Semester_Id(Long semesterId);

    List<SubjectScheduleInfo> findAllBySubject_Semester(Semester semester);

}
