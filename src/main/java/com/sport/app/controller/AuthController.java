package com.sport.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.app.entity.User;
import com.sport.app.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = authService.authenticate(email, password);
        Map<String, String> response = new HashMap<>();

        if (user != null) {
            response.put("status", "success");
            response.put("role", user.getClass().getSimpleName());
            response.put("id", String.valueOf(user.getId())); // Ajouter l'ID
            response.put("message", "Login réussi pour " + user.getClass().getSimpleName());
            return ResponseEntity.ok(response);
        
        } else {
            response.put("status", "error");
            response.put("message", "Email ou mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
