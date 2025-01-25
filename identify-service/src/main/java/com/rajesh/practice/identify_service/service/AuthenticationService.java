package com.rajesh.practice.identify_service.service;

import com.rajesh.practice.identify_service.entity.UserCredential;
import com.rajesh.practice.identify_service.repository.UserCredentialRepository;
import com.rajesh.practice.identify_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String saveNewUser(UserCredential userCredential){
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredential.setRoles(List.of("USER"));
        repository.save(userCredential);
        return "User added!!";
    }


    public String generateToken(String username){
        return jwtUtil.generateToken(username);
    }

    public boolean validateToken(String token){
        return jwtUtil.validateToken(token);
    }






}
