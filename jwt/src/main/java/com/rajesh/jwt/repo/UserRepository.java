package com.rajesh.jwt.repo;

import com.rajesh.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT s FROM User s WHERE s.userName= :userName")
    User findByUserName(@Param("userName") String userName);
}
