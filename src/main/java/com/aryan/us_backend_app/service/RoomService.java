package com.aryan.us_backend_app.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.aryan.us_backend_app.DbOperation.UserOperation;
import com.aryan.us_backend_app.constants.Constants;
import com.aryan.us_backend_app.constants.JwtUtil;
import com.aryan.us_backend_app.model.RoomModel;

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
}
