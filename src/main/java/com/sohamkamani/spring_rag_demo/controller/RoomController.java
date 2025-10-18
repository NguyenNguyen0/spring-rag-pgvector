package com.sohamkamani.spring_rag_demo.controller;

import com.sohamkamani.spring_rag_demo.entity.Room;
import com.sohamkamani.spring_rag_demo.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Room> getRoomByCode(@PathVariable String code) {
        Optional<Room> room = roomService.getRoomByCode(code);
        return room.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Room>> getRoomsByStatus(@PathVariable String status) {
        List<Room> rooms = roomService.getRoomsByStatus(status);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/floor/{floor}")
    public ResponseEntity<List<Room>> getRoomsByFloor(@PathVariable Integer floor) {
        List<Room> rooms = roomService.getRoomsByFloor(floor);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Room>> getRoomsByMinCapacity(@PathVariable Integer capacity) {
        List<Room> rooms = roomService.getRoomsByMinCapacity(capacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/refundable/{refundable}")
    public ResponseEntity<List<Room>> getRefundableRooms(@PathVariable Boolean refundable) {
        List<Room> rooms = roomService.getRefundableRooms(refundable);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        try {
            Room createdRoom = roomService.createRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        try {
            Room updatedRoom = roomService.updateRoom(id, roomDetails);
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
