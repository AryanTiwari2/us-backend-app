package com.aryan.us_backend_app.response;

import java.util.List;

public class UserRoomUpdateResponse {
    public String username;
    public List<String> roomNames;
    public String changedByUser;

    public UserRoomUpdateResponse(String username, List<String> roomNames, String changedByUser) {
        this.username = username;
        this.roomNames = roomNames;
        this.changedByUser = changedByUser;
    }
}
