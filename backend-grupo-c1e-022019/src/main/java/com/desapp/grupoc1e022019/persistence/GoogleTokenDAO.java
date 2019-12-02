package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.persistence.repositories.GoogleTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GoogleTokenDAO {

    @Autowired
    private GoogleTokenRepository googleTokenRepository;

    public GoogleToken save(GoogleToken googleAuth) {
        return googleTokenRepository.save(googleAuth);
    }

    public GoogleToken saveOrUpdate(GoogleToken googleAuth) {

        List<GoogleToken> results = googleTokenRepository.findByGoogleId(googleAuth.getGoogleId());


        if(results.isEmpty()) {
            return googleTokenRepository.save(googleAuth);
        }

        GoogleToken recover = googleTokenRepository.findByGoogleId(googleAuth.getGoogleId()).get(0);
        recover.setTokenId(googleAuth.getTokenId());
        recover.setExpires_in(googleAuth.getExpires_in());
        recover.setTokenAccess(googleAuth.getTokenAccess());

        return googleTokenRepository.save(recover);
    }

    public boolean existsGoogleId(String googleId) {
        return ! googleTokenRepository.findByGoogleId(googleId).isEmpty();
    }

    public boolean checkExistGoogleIdAndAuthToken(String googleId, String accessToken) {
        return ! googleTokenRepository.findByGoogleIdAndTokenAccess(googleId,accessToken).isEmpty();
    }

    public Long deleteAuthTokenByGoogleId(String googleId) {
        return googleTokenRepository.deleteByGoogleId(googleId);
    }

    public Integer deleteAllAuthTokenExpired() {
        return googleTokenRepository.deleteAllIfExpired();
    }
}
