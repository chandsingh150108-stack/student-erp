package com.studenterp.service;

import com.studenterp.entity.Room;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findAll() { return roomRepository.findAll(); }
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
    }
    public Room create(Room room) { return roomRepository.save(room); }
    public Room update(Long id, Room updated) {
        Room room = findById(id);
        room.setRoomNumber(updated.getRoomNumber());
        room.setBuilding(updated.getBuilding());
        room.setRoomType(updated.getRoomType());
        room.setCapacity(updated.getCapacity());
        return roomRepository.save(room);
    }
    public void delete(Long id) { roomRepository.deleteById(id); }
}
