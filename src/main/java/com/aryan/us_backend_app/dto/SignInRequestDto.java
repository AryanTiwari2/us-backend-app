package com.aryan.us_backend_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignInRequestDto {
    @NotBlank(message = "Username required")
    @Pattern(regexp = ".*@us$", message = "Username must end with @us")
    public String username;

    @NotBlank(message = "Password required")
    @Size(min=6, message = "Password must be at least 6 characters long")
    public String password;

    @NotBlank(message = "Confirm Password required")
    @Size(min=6, message = "Confirm Password must be at least 6 characters long")
    public String confirmPassword;

    @NotBlank(message = "Room Name required")
    public String roomName;
    

    public void isValidateReuqest() throws Exception {
        if(!password.equals(confirmPassword)) {
            throw new Exception("Password and Confirm Password do not match");
        }
    }

}
