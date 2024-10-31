package com.example.ebanking.controller;

import com.example.ebanking.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
public class TestController {
    @Autowired
    private EntityManager entityManager;

    private final HttpSession session;

    @Autowired
    public TestController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/hello")
    public String hello() {
        return session.getAttribute("user") != null ? "Hello, " + session.getAttribute("user") : "Hello, Guest!";
    }

    // Optional: you can also add another endpoint that returns JSON
    @GetMapping("/api/test")
    @Transactional
    public String testCreateUser() {
        try {
            User user = User.builder()
                    .username("testuser")
                    .password("password")
                    .email("test@test.com")
                    .firstName("Test")
                    .lastName("User")
                    .dateOfBirth(LocalDate.of(1990, 1, 1))
                    .status(true)
                    .build();

            entityManager.persist(user);
            return "User created successfully with ID: " + user.getId();
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
    }
}