package com.example.ScheduleGenerator.service;


import com.example.ScheduleGenerator.dto.ScheduleDto;
import com.example.ScheduleGenerator.mapper.ScheduleMapper;
import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final StudentGroupRepository groupRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           SubjectRepository subjectRepository,
                           TeacherRepository teacherRepository,
                           RoomRepository roomRepository,
                           StudentGroupRepository groupRepository) {
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
        this.groupRepository = groupRepository;
    }

    public ScheduleDto createSchedule(ScheduleDto dto) {
        // Зареждаме необходимите entity-та по ID
        Subject course = subjectRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + dto.getCourseId()));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + dto.getTeacherId()));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id " + dto.getRoomId()));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found with id " + dto.getGroupId()));

        Schedule schedule = ScheduleMapper.toEntity(dto, course, teacher, room, group);
        Schedule saved = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(saved);
    }

    public ScheduleDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));
        return ScheduleMapper.toDto(schedule);
    }

    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public ScheduleDto updateSchedule(Long id, ScheduleDto dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));

        // Актуализираме entity-то със зареждане на свързаните обекти, ако са подадени нови ID-та
        if (dto.getCourseId() != null) {
            Subject subject = subjectRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found with id " + dto.getCourseId()));
            schedule.setSubject(subject);
        }
        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id " + dto.getTeacherId()));
            schedule.setTeacher(teacher);
        }
        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found with id " + dto.getRoomId()));
            schedule.setRoom(room);
        }
        if (dto.getGroupId() != null) {
            StudentGroup group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found with id " + dto.getGroupId()));
            schedule.setGroup(group);
        }

        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setWeekParity(dto.getWeekParity());

        Schedule updated = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(updated);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}

