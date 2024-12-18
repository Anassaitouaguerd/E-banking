package com.example.ebanking.service.auth;

import com.example.ebanking.DTO.auth.*;
import com.example.ebanking.entity.User;
import com.example.ebanking.mapper.users.UserMapper;
import com.example.ebanking.repository.auth.AuthRepository;
import com.example.ebanking.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;
@Slf4j
@Service
@RequiredArgsConstructor
public class Auth {
    private final AuthRepository authRepository;
    private final UserMapper userMapper;
    private final HttpSession session;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = authRepository.login(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        LoginResponseDTO response = userMapper.toLoginResponseDTO(user);
        String jwtToken = jwtUtil.generateToken(response.getUsername());
        session.setAttribute("ticket", jwtToken);
        session.setAttribute("userDetails", response);
        System.out.println(session.getAttribute("ticket"));
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