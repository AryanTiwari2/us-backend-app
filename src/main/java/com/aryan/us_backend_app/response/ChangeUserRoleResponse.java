package com.aryan.us_backend_app.response;

public class ChangeUserRoleResponse {
    public String username;
    public String newRole;
    public String changedByUser;

    public ChangeUserRoleResponse(String username, String newRole, String changedByUser) {
        this.username = username;
        this.newRole = newRole;
        this.changedByUser = changedByUser;
    }
}
