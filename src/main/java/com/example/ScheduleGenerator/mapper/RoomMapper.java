package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.models.Room;


public class RoomMapper {

    public static RoomDto toDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setType(room.getType());
        return dto;
    }

    public static Room fromDTO(RoomDto dto) {
        Room room = new Room();
        room.setId(dto.getId());
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setType(dto.getType());
        return room;
    }
}
