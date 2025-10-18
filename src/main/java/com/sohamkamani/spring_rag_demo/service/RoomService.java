package com.sohamkamani.spring_rag_demo.service;

import com.sohamkamani.spring_rag_demo.entity.Room;
import com.sohamkamani.spring_rag_demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> getRoomByCode(String code) {
        return roomRepository.findByCode(code);
    }

    public List<Room> getRoomsByStatus(String status) {
        return roomRepository.findByStatus(status);
    }

    public List<Room> getRoomsByFloor(Integer floor) {
        return roomRepository.findByFloor(floor);
    }

    public List<Room> getRoomsByMinCapacity(Integer capacity) {
        return roomRepository.findByCapacityAdultsGreaterThanEqual(capacity);
    }

    public List<Room> getRefundableRooms(Boolean refundable) {
        return roomRepository.findByRefundable(refundable);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setName(roomDetails.getName());
                    room.setCode(roomDetails.getCode());
                    room.setCapacityAdults(roomDetails.getCapacityAdults());
                    room.setCapacityChildren(roomDetails.getCapacityChildren());
                    room.setSizeM2(roomDetails.getSizeM2());
                    room.setRefundable(roomDetails.getRefundable());
                    room.setFloor(roomDetails.getFloor());
                    room.setStatus(roomDetails.getStatus());
                    room.setDescription(roomDetails.getDescription());
                    return roomRepository.save(room);
                })
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
