package com.aryan.us_backend_app.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginUsingToken {
    @NotBlank(message = "Room Name required")
    public String roomName;
}
