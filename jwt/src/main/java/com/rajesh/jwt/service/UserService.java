package com.rajesh.jwt.service;

import com.rajesh.jwt.entity.User;
import com.rajesh.jwt.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;


    public User saveNewUser(User user){

        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            return repository.save(user);
        }catch (Exception e){
            return null;
        }

    }

    public List<User> getAllUser(){
        List<User> users = repository.findAll();
        return users;
    }


    public User getUserInfo(String userName){
        return repository.findByUserName(userName);
    }

    public void saveUser(User user){
        repository.save(user);
    }

}
