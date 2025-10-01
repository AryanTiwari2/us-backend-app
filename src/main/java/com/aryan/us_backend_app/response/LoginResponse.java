package com.aryan.us_backend_app.response;

import com.aryan.us_backend_app.model.RoomModel;
import com.aryan.us_backend_app.model.UserModel;

public class LoginResponse {
    public String token;
    public String username;
    public String userType;
    public String roomName;
    public Long userId;

    public LoginResponse(String token, UserModel user, RoomModel room) {
        this.token = token;
        this.username = user.username;
        this.userType = user.userType;
        this.roomName = room.roomName;
        this.userId = user.userId;
    }
}
