package com.aryan.us_backend_app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aryan.us_backend_app.DbOperation.UserOperation;
import com.aryan.us_backend_app.constants.Constants;
import com.aryan.us_backend_app.constants.JwtUtil;
import com.aryan.us_backend_app.dto.LoginRequestDto;
import com.aryan.us_backend_app.dto.SignInRequestDto;
import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;
import com.aryan.us_backend_app.response.LoginResponse;
import com.aryan.us_backend_app.response.UserRoomUpdateResponse;

import io.jsonwebtoken.Claims;

@Service
public class ProfileService {
    @Autowired
    private UserOperation userOperation;
    
    public LoginResponse signIn(SignInRequestDto request) throws Exception {
        RoomModel room = userOperation.getRoomByName(request.roomName);
        if(room == null) {
            throw new Exception("Room does not exist");
        }

        if(room.roomType.equals(Constants.RoomType.PRIVATE.toString())) {
            throw new Exception("Room is private");
        }

        UserModel user = userOperation.createUser(request.username, request.password, request.roomName);
        if(user == null) {
            throw new Exception("User already exists");
        }

        String token = JwtUtil.generateLoginToken(user, room);

        return new LoginResponse(token, user, room);
    }

    public LoginResponse login(LoginRequestDto request) throws Exception {
        UserModel user = userOperation.getUserByNameAndRoom(request.username, request.roomName);
        if(user == null) {
            throw new Exception("User does not exist");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(request.password, user.password)) {
            throw new Exception("Password does not match");
        }

        RoomModel room = userOperation.getRoomByName(request.roomName);

        String token = JwtUtil.generateLoginToken(user, room);
        return new LoginResponse(token, user, room);
    }

    public LoginResponse loginUsingToken(String roomName , String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String usernameStr = (String) claims.get("username");

        UserModel user = userOperation.getUserByNameAndRoom(usernameStr, roomName);

        if(user == null) {
            throw new Exception("User does not exist");
        }

        RoomModel room = userOperation.getRoomByName(roomName);

        String newToken = JwtUtil.generateLoginToken(user, room);
        return new LoginResponse(newToken, user, room);
    }

    public Claims parseToken(String token) throws Exception {
        return JwtUtil.parseToken(token);
    }

    public UserRoomUpdateResponse addUserRoom(String username, String[] roomName , String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String userTypeStr = (String) claims.get("userType");
        String usernameStr = (String) claims.get("username");

        if (userTypeStr == null || (!userTypeStr.equals(Constants.UserType.ADMIN.toString()) && !userTypeStr.equals(Constants.UserType.MODERATOR.toString())))
            throw new Exception("Only admin or moderator can user's update room");

        UserModel user = userOperation.getUserByName(username);
        if (user == null)
            throw new Exception("User does not exist");

        for(String room : roomName) {
            userOperation.updateUsersRoom(user, room);
        }
        UserModel userUpdated = userOperation.getUserByName(username);

        List<String> roomList = new ArrayList<>();
        for(RoomModel room : userUpdated.rooms) {
            roomList.add(room.roomName);
        };

        return new UserRoomUpdateResponse(userUpdated.username, roomList, usernameStr);

    }

    public UserRoomUpdateResponse removeUserRoom(String username, String[] roomName , String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        String userTypeStr = (String) claims.get("userType");
        String usernameStr = (String) claims.get("username");

        if (userTypeStr == null || (!userTypeStr.equals(Constants.UserType.ADMIN.toString()) && !userTypeStr.equals(Constants.UserType.MODERATOR.toString())))
            throw new Exception("Only admin or moderator can user's update room");

        UserModel user = userOperation.getUserByName(username);
        if (user == null)
            throw new Exception("User does not exist");

        for(String room : roomName) {
            userOperation.removeUserRoom(user, room);
        }

        UserModel userUpdated = userOperation.getUserByName(username);

        List<String> roomList = new ArrayList<>();
        for(RoomModel room : userUpdated.rooms) {
            roomList.add(room.roomName);
        };

        return new UserRoomUpdateResponse(userUpdated.username, roomList, usernameStr);

    }
}
