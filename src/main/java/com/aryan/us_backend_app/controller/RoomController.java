package com.aryan.us_backend_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.us_backend_app.dto.RoomRequestDto;
import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    
    @PostMapping("/create")
    public RoomModel createRoom(@Valid @RequestBody RoomRequestDto room , @RequestHeader("token") String token) throws Exception {
        return roomService.createRoom(token ,room.roomName, room.roomType.toString());
    }

    @PostMapping("/update")
    public RoomModel upadteRoom(@Valid @RequestBody RoomRequestDto room , @RequestHeader("token") String token) throws Exception {
        return roomService.updateRoom(token ,room.roomName, room.roomType.toString());
    }
}
