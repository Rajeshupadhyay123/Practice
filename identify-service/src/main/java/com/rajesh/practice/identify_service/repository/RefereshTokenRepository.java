package com.rajesh.practice.identify_service.repository;

import com.rajesh.practice.identify_service.entity.RefereshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefereshTokenRepository extends JpaRepository<RefereshToken,Integer> {
    Optional<RefereshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM referesh_token r WHERE r.user_id=:userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") int userCredId);
}
