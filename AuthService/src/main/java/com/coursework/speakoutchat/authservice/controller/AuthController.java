package com.coursework.speakoutchat.authservice.controller;

import com.coursework.speakoutchat.authservice.dto.AuthRequest;
import com.coursework.speakoutchat.authservice.entity.UserCredential;
import com.coursework.speakoutchat.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String addUser(@RequestBody UserCredential user) {
        if (user.getName().isBlank() || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("UserName and Password cannot be empty");
        }
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return service.generateToken(authRequest.getName());
        }
        throw new RuntimeException("Invalid access");
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
