package com.aryan.us_backend_app.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aryan.us_backend_app.DbOperation.UserOperation;
import com.aryan.us_backend_app.constants.Constants;
import com.aryan.us_backend_app.constants.JwtUtil;
import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;
import com.aryan.us_backend_app.response.AllUsersInRoom;

import io.jsonwebtoken.Claims;

@Service
public class RoomService {
    @Autowired
    private UserOperation userOperation;

    public RoomModel createRoom(String token, String roomName, String roomType) throws Exception {

        Claims claims = JwtUtil.parseToken(token);
        String userTypeStr = (String) claims.get("userType");

        if (userTypeStr == null || !userTypeStr.equals(Constants.UserType.ADMIN.toString()))
            throw new Exception("Only admin can create room");

        return userOperation.createRoom(roomName, roomType);
    }

    public RoomModel updateRoom(String token, String roomName, String roomType) throws Exception {

        Claims claims = JwtUtil.parseToken(token);
        String userTypeStr = (String) claims.get("userType");

        if (userTypeStr == null || !userTypeStr.equals(Constants.UserType.ADMIN.toString()))
            throw new Exception("Only admin can update room");

        return userOperation.updateRoom(roomName, roomType);
    }

    public AllUsersInRoom getAllUserInRoom(String token) throws Exception {

        Claims claims = JwtUtil.parseToken(token);
        String roomNameFromToken = (String) claims.get("roomName");
        RoomModel room = userOperation.getRoomByName(roomNameFromToken);
        if (room == null) {
            throw new Exception("Room does not exist");
        }

        List<UserModel> users = userOperation.getAllUsersInRoom(room.roomId);
        return new AllUsersInRoom(room.roomName, room.roomType, users);
    }
}
