package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.services.dtos.GoogleAuthDTO;

import java.time.LocalDateTime;

public class GoogleAuthBuilder {

    private String googleId;
    private String tokenId;
    private String accessToken;
    private LocalDateTime expires_in;

    public GoogleAuthBuilder() {}

    public GoogleToken build() {
        return new GoogleToken(googleId,tokenId,accessToken,expires_in);
    }

    public GoogleToken build(GoogleAuthDTO googleAuthDTO) {
        LocalDateTime expires_date_time = LocalDateTime.now().plusSeconds(googleAuthDTO.getExpires_in());
        return new GoogleToken(googleAuthDTO.getGoogleId(), googleAuthDTO.getTokenId(), googleAuthDTO.getTokenAccess(), expires_date_time);
    }

    public GoogleAuthBuilder withGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public GoogleAuthBuilder withTokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public GoogleAuthBuilder withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public GoogleAuthBuilder withExpiresIn(LocalDateTime expires_in) {
        this.expires_in = expires_in;
        return this;
    }

}
