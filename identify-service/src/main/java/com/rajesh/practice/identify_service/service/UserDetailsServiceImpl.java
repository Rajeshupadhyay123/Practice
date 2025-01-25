package com.rajesh.practice.identify_service.service;

import com.rajesh.practice.identify_service.entity.UserCredential;
import com.rajesh.practice.identify_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = userRepository.findByUserName(username);

        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

        throw new UsernameNotFoundException("User not Found with UserName: "+username);
    }
}
