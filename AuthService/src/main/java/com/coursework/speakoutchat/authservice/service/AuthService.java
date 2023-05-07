package com.coursework.speakoutchat.authservice.service;

import com.coursework.speakoutchat.authservice.entity.UserCredential;
import com.coursework.speakoutchat.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserCredentialRepository repository;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    @Autowired
    public AuthService(UserCredentialRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String saveUser(UserCredential credential) {
        credential.setPassword(encoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added";
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
