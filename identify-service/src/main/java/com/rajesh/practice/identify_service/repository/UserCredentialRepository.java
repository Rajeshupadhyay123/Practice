package com.rajesh.practice.identify_service.repository;

import com.rajesh.practice.identify_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {

    @Query("SELECT s FROM UserCredential s WHERE s.name= :userName")
    UserCredential findByUserName(@Param("userName") String userName);
}
