package com.desapp.grupoc1e022019.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_tokens")
public class GoogleToken extends EntityId{

    @Column(unique = true, nullable = false)
    private String googleId;
    @Column(nullable = false, length = 1300)
    private String tokenId;
    @Column(nullable = false)
    private String accessToken;
    private LocalDateTime expires_in;

    public GoogleToken(){}

    public GoogleToken(String googleId, String tokenId, String accessToken, LocalDateTime expires_in){
        setGoogleId(googleId);
        setTokenId(tokenId);
        setAccessToken(accessToken);
        setExpires_in(expires_in);
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(LocalDateTime expires_in) {
        this.expires_in = expires_in;
    }
}
