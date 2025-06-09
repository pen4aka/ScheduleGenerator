package com.example.ScheduleGenerator.service;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.mapper.RoomMapper;
import com.example.ScheduleGenerator.models.Room;
import com.example.ScheduleGenerator.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    public RoomDto getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id " + id));
        return RoomMapper.toDto(room);
    }
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoomDto createRoom(RoomDto dto) {
        Room room = RoomMapper.toEntity(dto);
        Room saved = roomRepository.save(room);
        return RoomMapper.toDto(saved);
    }

    public RoomDto updateRoom(Long id, RoomDto dto) {
        Room room = roomRepository.findById(id).orElseThrow();
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setHasComputers(Boolean.TRUE.equals(dto.getHasComputers()));
        room.setHasProjector(Boolean.TRUE.equals(dto.getHasProjector()));
        return RoomMapper.toDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}