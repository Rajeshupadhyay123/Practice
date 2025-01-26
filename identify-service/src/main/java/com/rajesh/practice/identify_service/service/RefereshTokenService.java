package com.rajesh.practice.identify_service.service;

import com.rajesh.practice.identify_service.entity.RefereshToken;
import com.rajesh.practice.identify_service.entity.UserCredential;
import com.rajesh.practice.identify_service.repository.RefereshTokenRepository;
import com.rajesh.practice.identify_service.repository.UserCredentialRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefereshTokenService {

    private Logger logger = LogManager.getLogger(RefereshTokenService.class);

    @Autowired
    private RefereshTokenRepository refereshTokenRepository;


    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public RefereshToken createRefereshToken(String userName){
        RefereshToken refereshToken = RefereshToken.builder()
                .userCredential(userCredentialRepository.findByUserName(userName))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refereshTokenRepository.save(refereshToken);
    }

    public Optional<RefereshToken> findByToken(String token){
        return refereshTokenRepository.findByToken(token);
    }


    public RefereshToken verifyExpiration(RefereshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refereshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+" Refresh Token was expired. Please make a new signin request");
        }
        return token;
    }

    public void deleteRefreshToken(int userCredId){
        logger.info("Cred is : "+userCredId);
        refereshTokenRepository.deleteByUserId(userCredId);
    }


}
