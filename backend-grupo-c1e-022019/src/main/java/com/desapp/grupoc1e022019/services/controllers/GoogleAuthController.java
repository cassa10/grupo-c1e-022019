package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.dtos.GoogleAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "googleAuthController")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    //TODO
    // MANAGE EXPIRE TOKEN SESSION

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity loginAuth(@RequestBody GoogleAuthDTO googleAuthDTO) {
        if(! googleAuthService.existGoogleId(googleAuthDTO.getGoogleId())) {
            return new ResponseEntity<>("Your account does not exist.Sign up, please", HttpStatus.UNAUTHORIZED);
        }
        GoogleToken googleAuth = buildGoogleToken(googleAuthDTO);

        googleAuthService.saveGoogleToken(googleAuth);

        return new ResponseEntity<>("Auth completed", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signupAuth(@RequestBody GoogleAuthDTO googleAuthDTO) {
        if(googleAuthService.existGoogleId(googleAuthDTO.getGoogleId())){
            return new ResponseEntity<>("Account already exists, please log in", HttpStatus.OK);
        }

        GoogleToken googleAuth = buildGoogleToken(googleAuthDTO);

        googleAuthService.saveGoogleToken(googleAuth);

        return new ResponseEntity<>("Auth completed", HttpStatus.OK);
    }

    private GoogleToken buildGoogleToken(GoogleAuthDTO googleAuthDTO) {
        return new GoogleToken(googleAuthDTO.getGoogleId(),
                googleAuthDTO.getTokenId(),
                googleAuthDTO.getAccessToken(),
                googleAuthDTO.getExpires_in()) ;
    }
}
