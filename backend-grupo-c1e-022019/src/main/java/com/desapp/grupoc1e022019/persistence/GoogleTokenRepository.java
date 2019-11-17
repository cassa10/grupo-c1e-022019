package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.GoogleToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoogleTokenRepository extends JpaRepository<GoogleToken,Long> {
    List<GoogleToken> findByGoogleId(String googleId);

    List<GoogleToken> findByGoogleIdAndAccessToken(String googleId, String accessToken);

    Long deleteByGoogleId(String googleId);
}
