package org.smortsogest.service;

import org.smortsogest.dto.auth.AuthResponse;
import org.smortsogest.dto.auth.LoginRequest;
import org.smortsogest.dto.auth.RegisterRequest;
import org.smortsogest.dto.UserDTO;

public interface AuthService {
    AuthResponse registerUser(RegisterRequest registerRequest);
    AuthResponse loginUser(LoginRequest loginRequest);
}
