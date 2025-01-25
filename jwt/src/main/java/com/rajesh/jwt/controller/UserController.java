package com.rajesh.jwt.controller;

import com.rajesh.jwt.entity.User;
import com.rajesh.jwt.security.JwtUtil;
import com.rajesh.jwt.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity<String> getStatus(){
        return ResponseEntity.ok("UP!!");
    }

    /*
        How we reached this secure endpoint.
        1. First it check the prefix of endpoint and move to the SecurityFilterChain to verify that
        does this endpoint is secured or public.

        2. Then check the method of SpringSecurity class : securityFilterChain() method

        Final request come back to this controller.

        12. Here SecurityContextHolder has all the information about the user which has login and has
        access for this url endpoint.
     */
    @GetMapping("/user/info")
    public ResponseEntity<?> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = service.getUserInfo(name);
        if(user!=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/user/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = service.getAllUser();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        User saveuser = service.saveNewUser(user);
        return new ResponseEntity<>(saveuser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){

        logger.info("User Authentication start........");

        //Here we are creating the bean of AuthenticationManager and with help of this we are
        //passing the information of UsernamePasswordAuthenticationToken object this request is handle by
        //ProviderManager to authenticate the user on the behalf of username and password
        //For understanding this flow check on SpringSecurity class, bean of ProviderManager
        try{
            //Here if the user is not authenticate by username and password then it will give exception
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            /*
                Once the user get verify by the above authentication method by username and password,
                then we need to take the UserDetails and get the userName and pass it to create token.
             */
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception occurred while creatingAuthenticationToken "+e);
            return new ResponseEntity<>("Incorrect username and password",HttpStatus.BAD_GATEWAY);
        }


    }
}
