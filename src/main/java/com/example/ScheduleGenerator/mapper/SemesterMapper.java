package com.example.ScheduleGenerator.mapper;
import com.example.ScheduleGenerator.dto.SemesterDto;
import com.example.ScheduleGenerator.models.Semester;

public class SemesterMapper {
    public static SemesterDto toDto(Semester s) {
        if (s==null) return null;
        SemesterDto d = new SemesterDto();
        d.setId(s.getId()); d.setSemesterNo(s.getSemesterNo());
        return d;
    }
    public static Semester toEntity(SemesterDto d) {
        if (d == null) return null;
        Semester s = new Semester();
        s.setSemesterNo(d.getSemesterNo());
        return s;
    }
}