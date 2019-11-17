package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.GoogleToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenDAO {

    @Autowired
    private GoogleTokenRepository googleTokenRepository;

    public GoogleToken save(GoogleToken googleAuth) {
        return googleTokenRepository.save(googleAuth);
    }

    public boolean existsGoogleId(String googleId) {
        return ! googleTokenRepository.findByGoogleId(googleId).isEmpty();
    }

    public boolean checkInvalidAuthToken(String googleId, String accessToken) {
        return ! googleTokenRepository.findByGoogleIdAndAccessToken(googleId,accessToken).isEmpty();
    }

    public Long deleteAuthTokenByGoogleId(String googleId) {
        return googleTokenRepository.deleteByGoogleId(googleId);
    }
}
