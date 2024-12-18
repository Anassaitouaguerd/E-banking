package com.example.ebanking.controller.crud;

import com.example.ebanking.util.JwtUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TestOauth {

    @PostMapping("/getData")
    public ResponseEntity<Map<String, Object>> getData(HttpSession httpSession) {
        Object userTken = httpSession.getAttribute("token");
        String username = new JwtUtil().extractUserName(userTken.toString());
        System.out.println(username);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
//        response.put("token", user);

        return ResponseEntity.ok(response);
    }
}
