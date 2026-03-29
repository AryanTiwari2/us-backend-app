package com.aryan.us_backend_app.response;

import java.util.List;

import com.aryan.us_backend_app.model.UserModel;

public class AllUsersInRoom {
    public String roomName;
    public String roomType;
    public int totalUsers;
    public List<ProfilePageResponse> users;

    public AllUsersInRoom(String roomName, String roomType, List<UserModel> users) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.totalUsers = users.size();
        this.users = users.stream().map(user -> new ProfilePageResponse(user, roomName)).toList();
    }
}
