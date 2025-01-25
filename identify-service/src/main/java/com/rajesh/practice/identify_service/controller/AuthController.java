package com.rajesh.practice.identify_service.controller;

import com.rajesh.practice.identify_service.entity.UserCredential;
import com.rajesh.practice.identify_service.service.AuthenticationService;
import com.rajesh.practice.identify_service.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @GetMapping("/status")
    public ResponseEntity<String> getStatus(){
        return new ResponseEntity<>("UP!!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserCredential user){
        String s = service.saveNewUser(user);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody UserCredential user){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

            String jwt = service.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid Username and Password", HttpStatus.BAD_REQUEST);
        }


    }


    @GetMapping("/validate")
    public String validateToken(@RequestBody UserCredential user){
        boolean validated = service.validateToken(user.getName());
        if(validated){
            return "Token is valid";
        }else{
            return "Token has expired";
        }

    }










}
