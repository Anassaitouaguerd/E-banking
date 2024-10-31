package com.example.ebanking.service.auth;

import com.example.ebanking.entity.User;
import com.example.ebanking.repository.auth.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Auth {
    private AuthRepository authRepository;

    @Autowired
    public Auth(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User login(String email , String password) {
        return authRepository.login(email, password)
                                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public void register(User user) {
        authRepository.register(user);
    }

    public User findByEmail(String email) {
        return authRepository.findByEmail(email)
                                    .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
