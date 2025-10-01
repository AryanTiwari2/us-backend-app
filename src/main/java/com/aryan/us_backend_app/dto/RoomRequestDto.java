package com.aryan.us_backend_app.dto;

import com.aryan.us_backend_app.constants.Constants.RoomType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequestDto {
    @NotBlank(message = "Room Name required")
    public String roomName;

    @NotNull(message = "Room Type required")
    public RoomType roomType;
}
