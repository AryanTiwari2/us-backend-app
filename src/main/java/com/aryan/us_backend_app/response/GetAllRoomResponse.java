package com.aryan.us_backend_app.response;

import com.aryan.us_backend_app.model.RoomModel;

import java.util.List;

public class GetAllRoomResponse {
    public List<RoomModel> rooms;
    
    public GetAllRoomResponse(List<RoomModel> rooms) {
        this.rooms = rooms;
    }
}
