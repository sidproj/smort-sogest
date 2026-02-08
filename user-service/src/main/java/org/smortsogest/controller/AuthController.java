package org.smortsogest.controller;

import org.smortsogest.dto.auth.AuthResponse;
import org.smortsogest.dto.auth.LoginRequest;
import org.smortsogest.dto.auth.RegisterRequest;
import org.smortsogest.dto.UserDTO;
import org.smortsogest.service.AuthServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    private final AuthServiceImpl userServiceImpl;

    public AuthController(AuthServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public AuthResponse registerUser(@Validated @RequestBody RegisterRequest registerRequest){
        return userServiceImpl.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@Validated @RequestBody LoginRequest loginRequest){
        return userServiceImpl.loginUser(loginRequest);
    }
}
