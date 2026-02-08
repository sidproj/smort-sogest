package org.smortsogest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.smortsogest.dto.auth.AuthResponse;
import org.smortsogest.dto.auth.RegisterRequest;
import org.smortsogest.dto.UserDTO;
import org.smortsogest.exception.auth.DuplicateEmailException;
import org.smortsogest.model.User;
import org.smortsogest.repository.UserRepository;
import org.smortsogest.security.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldRegisterUser(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("Test User");
        request.setPassword("password123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Test User");
        savedUser.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        AuthResponse result = authService.registerUser(request);

        String expectedToken =jwtUtil.generateToken(
                result.getUserDTO().getId().toString(),
                Map.of("email",request.getEmail()));

        assertNotNull(result);
        assertEquals(request.getEmail(),result.getUserDTO().getEmail());
        assertEquals(request.getName(),result.getUserDTO().getName());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(jwtUtil.getExpirationInSeconds(),result.getExpiresIn());

        assertEquals(expectedToken,result.getAccessToken());
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowDuplicateEmailException(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("Duplicate"));

        assertThrows(DuplicateEmailException.class,()->authService.registerUser(request));
    }
}
