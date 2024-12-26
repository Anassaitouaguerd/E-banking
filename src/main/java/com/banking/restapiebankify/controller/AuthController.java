package com.banking.restapiebankify.controller;

import com.banking.restapiebankify.config.JwtTokenProvider;
import com.banking.restapiebankify.dto.LoginRequest;
import com.banking.restapiebankify.dto.UserDTO;
import com.banking.restapiebankify.model.User;
import com.banking.restapiebankify.service.AuthService;
import com.banking.restapiebankify.service.UserService;
import com.banking.restapiebankify.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO UserDTO) {
        User user = authService.registerUser(UserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = authService.findUserByUsername(loginRequest.getUsername());
        String jwt = jwtTokenProvider.generateToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("userId", user.getId().toString());
        response.put("role", user.getRole().getName());

        return ResponseEntity.ok(response);

    }
}