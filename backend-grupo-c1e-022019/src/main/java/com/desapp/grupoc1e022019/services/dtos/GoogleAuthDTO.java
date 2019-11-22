package com.desapp.grupoc1e022019.services.dtos;

public class GoogleAuthDTO {

    private String googleId;
    private String tokenId;
    private String accessToken;
    private Integer expires_in;

    public GoogleAuthDTO() {
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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public String toString() {
        return "{ googleId: "+googleId+", tokenId: "+tokenId + ", accessToken :"+accessToken+ ", expires_in: "+ expires_in +" }";
    }
}
