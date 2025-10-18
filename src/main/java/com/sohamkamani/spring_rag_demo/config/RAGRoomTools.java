package com.sohamkamani.spring_rag_demo.config;

import com.sohamkamani.spring_rag_demo.entity.Room;
import com.sohamkamani.spring_rag_demo.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGRoomTools {
    private final Logger logger = LoggerFactory.getLogger(RAGRoomTools.class);
    private final RoomService roomService;

    public RAGRoomTools(RoomService roomService) {
        this.roomService = roomService;
    }

    @Tool(name = "getAllRooms", description = "Lấy toàn bộ danh sách phòng trong khách sạn Aurora")
    public List<Room> getAllRooms() {
        logger.debug("🔨Calling [getAllRooms]...");
        return roomService.getAllRooms();
    }

    @Tool(name = "getRoomByCode", description = "Lấy toàn thông tin phòng trong khách sạn Aurora bằng mã code")
    public Room getRoomByCode(@ToolParam(required = true, description = "Mã code của phòng") String code) {
        logger.debug("🔨Calling [getRoomByCode]...");
        return roomService.getRoomByCode(code).orElse(null);
    }
}