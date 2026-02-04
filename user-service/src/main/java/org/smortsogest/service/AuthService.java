package org.smortsogest.service;

import org.smortsogest.dto.LoginRequest;
import org.smortsogest.dto.RegisterRequest;
import org.smortsogest.dto.UserDTO;

public interface AuthService {
    UserDTO registerUser(RegisterRequest registerRequest);
    UserDTO loginUser(LoginRequest loginRequest);
}
