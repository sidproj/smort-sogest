package org.smortsogest.service;

import org.smortsogest.dto.LoginRequest;
import org.smortsogest.exception.DuplicateEmailException;
import org.smortsogest.mapper.UserMapper;
import org.smortsogest.dto.RegisterRequest;
import org.smortsogest.dto.UserDTO;
import org.smortsogest.model.User;
import org.smortsogest.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword());
        try {
            this.userRepository.save(user);
            return UserMapper.toUserDTO(user);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicateEmailException();
        }
    }

    @Override
    public UserDTO loginUser(LoginRequest loginRequest) {
        return null;
    }
}
