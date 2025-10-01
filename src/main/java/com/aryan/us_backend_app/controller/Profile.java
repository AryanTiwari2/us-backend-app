package com.aryan.us_backend_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.us_backend_app.dto.LoginRequestDto;
import com.aryan.us_backend_app.dto.LoginUsingToken;
import com.aryan.us_backend_app.dto.SignInRequestDto;
import com.aryan.us_backend_app.dto.UpdateUserRoom;
import com.aryan.us_backend_app.response.LoginResponse;
import com.aryan.us_backend_app.response.UserRoomUpdateResponse;
import com.aryan.us_backend_app.service.ProfileService;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile")
public class Profile {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/signIn")
    public LoginResponse signIn(@Valid @RequestBody SignInRequestDto request) throws Exception {
        request.isValidateReuqest();
        return profileService.signIn(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequestDto request) throws Exception {
        return profileService.login(request);
    }

    @PostMapping("/login/token")
    public LoginResponse loginUsingToken(@Valid @RequestBody LoginUsingToken request , @RequestHeader("token") String token) throws Exception {
        return profileService.loginUsingToken(request.roomName, token);
    }

    @GetMapping("/parseToken")
    public Claims parseToken(@RequestHeader("token") String token) throws Exception {
        return profileService.parseToken(token);
    }

    @PostMapping("/updateUserRoom")
    public UserRoomUpdateResponse addUserRoom(@Valid @RequestBody UpdateUserRoom request , @RequestHeader("token") String token) throws Exception {
        return profileService.addUserRoom(request.username, request.roomName, token);
    }

    @PostMapping("/removeUserRoom")
    public UserRoomUpdateResponse removeUserRoom(@Valid @RequestBody UpdateUserRoom request , @RequestHeader("token") String token) throws Exception {
        return profileService.removeUserRoom(request.username, request.roomName, token);
    }

    @GetMapping("/health")
    public String test() {
        return "health check";
    }
}