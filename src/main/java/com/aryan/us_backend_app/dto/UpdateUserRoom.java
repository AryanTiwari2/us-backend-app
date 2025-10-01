package com.aryan.us_backend_app.dto;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRoom {

    @NotBlank(message = "Username required")
    public String username;

    @NonNull
    public String[] roomName;
}
