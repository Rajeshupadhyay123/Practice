package com.rajesh.practice.identify_service.controller;

import com.rajesh.practice.identify_service.entity.RefereshToken;
import com.rajesh.practice.identify_service.entity.UserCredential;
import com.rajesh.practice.identify_service.exception.UserNotFoundException;
import com.rajesh.practice.identify_service.response.JwtResponse;
import com.rajesh.practice.identify_service.response.RefreshTokenRequest;
import com.rajesh.practice.identify_service.service.AuthenticationService;
import com.rajesh.practice.identify_service.service.RefereshTokenService;
import com.rajesh.practice.identify_service.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private RefereshTokenService refereshTokenService;


    @GetMapping("/status")
    public ResponseEntity<String> getStatus(){
        return new ResponseEntity<>("UP!!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserCredential user){
        String s = service.saveNewUser(user);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }


    @GetMapping("/checkValidation")
    public ResponseEntity<String> statusCheck(){
        String check="check";
        return new ResponseEntity<>("Working",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> getToken(@RequestBody UserCredential user){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword())
            );


            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

            //For deleting Refresh token for second time login to regenerate
            int userId = service.findUserIdByUserName(user.getName());
            refereshTokenService.deleteRefreshToken(userId);


            //For creating the Refresh Token
            RefereshToken refereshToken = refereshTokenService.createRefereshToken(userDetails.getUsername());

            String jwt = service.generateToken(userDetails.getUsername());

            JwtResponse response = new JwtResponse(jwt, refereshToken.getToken());

            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch (Exception e){
             throw new UserNotFoundException("Invalid User Request");
        }


    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
           return refereshTokenService.findByToken(refreshTokenRequest.getToken())
                    .map(refereshTokenService::verifyExpiration)
                    .map(RefereshToken::getUserCredential)
                    .map(userCredential -> {
                        String accessToken = service.generateToken(userCredential.getName());
                        return new JwtResponse(accessToken,refreshTokenRequest.getToken());
                    }).orElseThrow(()-> new RuntimeException("Refresh token is not in database"));
    }

    @PostMapping("/validate/token/{token}")
    public boolean validateToken(@PathVariable("token") String token){
        return service.validateToken(token);
    }










}
