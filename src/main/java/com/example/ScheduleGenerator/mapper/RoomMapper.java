package com.example.ScheduleGenerator.mapper;

import com.example.ScheduleGenerator.dto.RoomDto;
import com.example.ScheduleGenerator.models.Room;


public class RoomMapper {

    private RoomMapper() {
    }

    public static RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setHasComputers(room.isHasComputers());
        dto.setHasProjector(room.isHasProjector());
        return dto;
    }

    public static Room toEntity(RoomDto dto) {
        if (dto == null) {
            return null;
        }
        Room room = new Room();
        room.setId(dto.getId());
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setHasComputers(Boolean.TRUE.equals(dto.getHasComputers()));
        room.setHasProjector(Boolean.TRUE.equals(dto.getHasProjector()));
        return room;
    }
}
