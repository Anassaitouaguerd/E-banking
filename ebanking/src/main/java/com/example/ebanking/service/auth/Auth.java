package com.example.ebanking.service.auth;

import com.example.ebanking.DTO.auth.*;
import com.example.ebanking.entity.User;
import com.example.ebanking.mapper.users.UserMapper;
import com.example.ebanking.repository.auth.AuthRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Auth {
    private final AuthRepository authRepository;
    private final UserMapper userMapper;
    private final HttpSession session;

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = authRepository.login(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        LoginResponseDTO response = userMapper.toLoginResponseDTO(user);
        session.setAttribute("user", response);
        return response;
    }

    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO request) {
        // Check if user exists
        authRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("User already exists");
                });

        // Create new user
        User user = userMapper.toEntity(request);
        authRepository.register(user);

        return userMapper.toLoginResponseDTO(user);
    }

    @Transactional
    public void logout() {
        session.removeAttribute("user");
        session.invalidate();
    }
}