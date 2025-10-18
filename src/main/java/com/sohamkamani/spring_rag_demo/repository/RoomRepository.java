package com.sohamkamani.spring_rag_demo.repository;

import com.sohamkamani.spring_rag_demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByCode(String code);

    List<Room> findByStatus(String status);

    List<Room> findByFloor(Integer floor);

    List<Room> findByCapacityAdultsGreaterThanEqual(Integer capacity);

    List<Room> findByRefundable(Boolean refundable);
}

