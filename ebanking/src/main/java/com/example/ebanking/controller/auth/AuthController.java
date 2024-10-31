package com.example.ebanking.controller.auth;

import com.example.ebanking.entity.User;
import com.example.ebanking.service.auth.Auth;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private Auth auth;
    private final HttpSession session;

    @Autowired
    public AuthController(Auth auth , HttpSession session) {
        this.auth = auth;
        this.session = session;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            User user = auth.login(email, password);
            if (user != null) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("username", user.getUsername());
                userInfo.put("email", user.getEmail());
                userInfo.put("role", user.getRole());
                session.setAttribute("user", userInfo);
                return ResponseEntity.ok(user);
            }
                return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Invalid credentials"
                    ));
        }catch (Exception e){
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "An error occurred during login"
                    ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try{
            User userExists = auth.findByEmail(user.getEmail());
            if(userExists != null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                     .body(Map.of("message", "User already exists"));
            }
            auth.register(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(Map.of("message", "User registered successfully"));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "An error occurred during registration"
                    ));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            // Clear specific session attributes
            session.removeAttribute("user");

            // Invalidate the current session
            session.invalidate();

            return ResponseEntity.ok(Map.of(
                    "message", "Logged out successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "An error occurred during logout"
                    ));
        }
    }
}
