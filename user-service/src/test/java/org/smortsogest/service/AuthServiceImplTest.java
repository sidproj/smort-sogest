package org.smortsogest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.smortsogest.dto.auth.AuthResponse;
import org.smortsogest.dto.auth.LoginRequest;
import org.smortsogest.dto.auth.RegisterRequest;
import org.smortsogest.exception.UserNotFoundException;
import org.smortsogest.exception.auth.DuplicateEmailException;
import org.smortsogest.exception.auth.PasswordDoNotMatchException;
import org.smortsogest.model.User;
import org.smortsogest.repository.UserRepository;
import org.smortsogest.security.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

//    register

    @Test
    void shouldRegisterUser(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("Test User");
        request.setPassword("12345");
        request.setConfirmPassword("12345");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("HASHED_PASSWORD");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        when(passwordEncoder.encode(any(String.class))).thenReturn("HASHED_PASSWORD");

        AuthResponse result = authService.registerUser(request);

        assertNotNull(result);
        assertEquals(request.getEmail(),result.getUserDTO().getEmail());
        assertEquals(request.getName(),result.getUserDTO().getName());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(jwtUtil.getExpirationInSeconds(),result.getExpiresIn());

        verify(passwordEncoder,times(1)).encode(any(String.class));
        verify(userRepository,times(1)).save(any(User.class));
        verify(userRepository).save(argThat(user ->
                user.getPassword().equals("HASHED_PASSWORD")));
    }

    @Test
    void shouldThrowPasswordDoNotMatch(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("12345");
        request.setConfirmPassword("123456");

        assertThrows(PasswordDoNotMatchException.class,()-> authService.registerUser(request));
    }

    @Test
    void shouldThrowDuplicateEmailException(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("12345");
        request.setConfirmPassword("12345");

        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("Duplicate"));

        assertThrows(DuplicateEmailException.class,()->authService.registerUser(request));
    }

//    login

    @Test
    void shouldLoginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("sid@gmail.com");
        loginRequest.setPassword("1234");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Test");
        existingUser.setEmail("sid@gmail.com");
        existingUser.setPassword("1234");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(existingUser));

        when(passwordEncoder.matches(any(String.class),any(String.class))).thenReturn(true);

        AuthResponse result = authService.loginUser(loginRequest);

        assertNotNull(result);
        assertEquals(loginRequest.getEmail(),result.getUserDTO().getEmail());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(jwtUtil.getExpirationInSeconds(),result.getExpiresIn());
        verify(userRepository,times(1)).findByEmail(any(String.class));
    }

    @Test
    void shouldThrowUserNotFoundException(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("sid@gmail.com");
        loginRequest.setPassword("1234");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->authService.loginUser(loginRequest));
    }

    @Test
    void shouldThrowPasswordDoNotMatchException(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("sid@gmail.com");
        loginRequest.setPassword("1234");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Test");
        existingUser.setEmail("sid@gmail.com");
        existingUser.setPassword("1234");

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(existingUser));

        when(passwordEncoder.matches(any(String.class),any(String.class))).thenReturn(false);

        assertThrows(PasswordDoNotMatchException.class,()->authService.loginUser(loginRequest));
    }

}
