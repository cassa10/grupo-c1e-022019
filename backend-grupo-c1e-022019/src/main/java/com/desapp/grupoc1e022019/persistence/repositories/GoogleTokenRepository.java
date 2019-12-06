package com.desapp.grupoc1e022019.persistence.repositories;

import com.desapp.grupoc1e022019.model.GoogleToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoogleTokenRepository extends JpaRepository<GoogleToken,Long> {
    List<GoogleToken> findByGoogleId(String googleId);

    Optional<GoogleToken> findByGoogleIdAndTokenAccess(String googleId, String accessToken);

    Long deleteByGoogleId(String googleId);

    @Modifying
    @Query("delete from GoogleToken gt where gt.expires_in <= CURRENT_TIMESTAMP")
    Integer deleteAllIfExpired();
}
