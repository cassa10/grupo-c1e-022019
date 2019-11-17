package com.desapp.grupoc1e022019.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auth_tokens")
public class GoogleToken extends EntityId{

    @Column(unique = true, nullable = false)
    private String googleId;
    @Column(nullable = false)
    private String tokenId;
    @Column(nullable = false)
    private String accessToken;
    private Integer expires_in;

    public GoogleToken(){}

    public GoogleToken(String googleId, String tokenId, String accessToken, Integer expires_in){
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

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
