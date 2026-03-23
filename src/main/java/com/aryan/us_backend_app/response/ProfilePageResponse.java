package com.aryan.us_backend_app.response;

import com.aryan.us_backend_app.model.UserModel;

public class ProfilePageResponse {
    public Long userId;
    public String username;
    public String roomName;
    public String userType;
    public String gender;
    public String description;
    public String name;
    public String image;
    public String socialLink;
    public String themeColor;

    public ProfilePageResponse(UserModel user, String roomName) {
        this.userId = user.userId;
        this.username = user.username;
        this.roomName = roomName;
        this.userType = user.userType;
        this.gender = user.gender;
        this.description = user.description;
        this.name = user.name;
        this.image = user.image;
        this.socialLink = user.socialLink;
        this.themeColor = user.themeColor;
    }

}
