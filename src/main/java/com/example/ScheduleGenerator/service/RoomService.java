package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.mapper.RoomMapper;
import com.example.ScheduleGenerator.models.Room;
import com.example.ScheduleGenerator.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public List<RoomDto> getAll() {
        return roomRepo.findAll().stream()
                .map(RoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoomDto create(RoomDto dto) {
        Room room = RoomMapper.fromDTO(dto);
        room = roomRepo.save(room);
        return RoomMapper.toDTO(room);
    }
}