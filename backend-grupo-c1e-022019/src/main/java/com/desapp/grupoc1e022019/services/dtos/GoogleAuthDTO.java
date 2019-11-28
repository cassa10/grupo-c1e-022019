package com.desapp.grupoc1e022019.services.dtos;

public class GoogleAuthDTO {

    private String googleId;
    private String tokenId;
    private String tokenAccess;
    private Integer expires_in;

    public GoogleAuthDTO() {
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
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
        return "{ googleId: "+googleId+", tokenId: "+tokenId + ", accessToken :"+ tokenAccess + ", expires_in: "+ expires_in +" }";
    }
}
