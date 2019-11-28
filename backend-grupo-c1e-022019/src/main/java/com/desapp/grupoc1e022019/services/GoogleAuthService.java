package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.GoogleTokenDAO;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Scope(value = "session")
@Component(value = "googleAuthService")
public class GoogleAuthService {

    @Autowired
    private GoogleTokenDAO googleTokenDAO = new GoogleTokenDAO();

    @Autowired
    private ClientDAO clientDAO = new ClientDAO();

    @Autowired
    private ProviderDAO providerDAO = new ProviderDAO();

    @Transactional
    public void saveOrUpdateGoogleToken(GoogleToken googleAuth) {
        googleTokenDAO.saveOrUpdate(googleAuth);
    }

    public boolean existGoogleId(String googleId) {
        return googleTokenDAO.existsGoogleId(googleId);
    }

    public boolean checkExistAuthToken(GoogleToken googleToken) {
        return googleTokenDAO.checkExistGoogleIdAndAuthToken(googleToken.getGoogleId(),googleToken.getAccessToken());
    }

    @Transactional
    public void logoutGoogleToken(GoogleToken googleToken) {
        googleTokenDAO.deleteAuthTokenByGoogleId(googleToken.getGoogleId());
    }

    public boolean clientHasAccess(String googleId, String tokenAccess) {
        return clientDAO.existClientByGoogleId(googleId) && googleTokenDAO.checkExistGoogleIdAndAuthToken(googleId,tokenAccess);
    }

    public boolean providerHasAccess(String googleId, String tokenAccess) {
        return providerDAO.existProviderByGoogleId(googleId) && googleTokenDAO.checkExistGoogleIdAndAuthToken(googleId,tokenAccess);
    }

    public boolean userHasAccess(String googleId, String tokenAccess) {
        return googleTokenDAO.checkExistGoogleIdAndAuthToken(googleId,tokenAccess);
    }
}
