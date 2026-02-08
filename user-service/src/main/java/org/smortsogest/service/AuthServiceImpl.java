package org.smortsogest.service;

import jakarta.transaction.Transactional;
import org.smortsogest.dto.auth.AuthResponse;
import org.smortsogest.dto.auth.LoginRequest;
import org.smortsogest.exception.UserNotFoundException;
import org.smortsogest.exception.auth.DuplicateEmailException;
import org.smortsogest.exception.auth.PasswordDoNotMatchException;
import org.smortsogest.mapper.UserMapper;
import org.smortsogest.dto.auth.RegisterRequest;
import org.smortsogest.dto.UserDTO;
import org.smortsogest.model.User;
import org.smortsogest.repository.UserRepository;
import org.smortsogest.security.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest){
        User user = new User();

        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new PasswordDoNotMatchException();
        }

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(hashedPassword);

        try {
            User savedUser = this.userRepository.save(user);

            String token = jwtUtil.generateToken(savedUser.getId().toString(), Map.of("email",savedUser.getEmail()));

            AuthResponse authResponse = new AuthResponse();

            authResponse.setAccessToken(token);
            authResponse.setTokenType("Bearer");
            authResponse.setExpiresIn(jwtUtil.getExpirationInSeconds());
            authResponse.setUserDTO(UserMapper.toUserDTO(savedUser));

            return authResponse;
        }catch (DataIntegrityViolationException ex){
            throw new DuplicateEmailException();
        }
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new PasswordDoNotMatchException();
        }

        String token = jwtUtil.generateToken(user.getId().toString(),
                Map.of("email",user.getEmail()));

        AuthResponse authResponse = new AuthResponse();

        authResponse.setUserDTO(UserMapper.toUserDTO(user));
        authResponse.setAccessToken(token);
        authResponse.setExpiresIn(jwtUtil.getExpirationInSeconds());
        authResponse.setTokenType("Bearer");

        return authResponse;
    }
}
