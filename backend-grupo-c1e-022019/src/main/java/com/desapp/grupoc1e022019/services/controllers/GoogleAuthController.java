package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.model.clientState.NormalClient;
import com.desapp.grupoc1e022019.services.ClientService;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.builder.GoogleAuthBuilder;
import com.desapp.grupoc1e022019.services.dtos.ClientDTO;
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

    @Autowired
    private ClientService clientService = new ClientService();

    //TODO
    // MANAGE EXPIRE TOKEN SESSION

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity loginAuth(@RequestBody GoogleAuthDTO googleAuthDTO) {

        if(! clientService.existClientByGoogleId(googleAuthDTO.getGoogleId())) {
            return new ResponseEntity<>("Your account does not exist", HttpStatus.BAD_REQUEST);
        }

        GoogleToken googleAuth = new GoogleAuthBuilder().build(googleAuthDTO);

        googleAuthService.saveOrUpdateGoogleToken(googleAuth);

        return new ResponseEntity<>("Auth completed", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signupAuth(@RequestBody ClientDTO clientDTO) {
        if(clientService.existClientByGoogleId(clientDTO.getGoogleId())){
            return new ResponseEntity<>("Account already exists, please log in", HttpStatus.BAD_REQUEST);
        }

        GoogleToken googleAuth = new GoogleAuthBuilder().build(clientDTO.getGoogleAuthDTO());

        Client client = ClientBuilder.aClient()
                .withGoogleId(clientDTO.getGoogleId())
                .withImageUrl(clientDTO.getImageUrl())
                .withFirstName(clientDTO.getFirstName())
                .withLastName(clientDTO.getLastName())
                .withStateClient(new NormalClient())
                .withEmail(clientDTO.getEmail())
                .withCredit(new Credit())
                .build();

        clientService.saveClientAndGoogleAuth(client,googleAuth);

        return new ResponseEntity<>("Sign up completed", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public ResponseEntity logoutAuth(@RequestBody GoogleAuthDTO googleAuthDTO) {

        // TODO
        //  ASPECT AUTHENTICATION
        GoogleToken googleToken = new GoogleAuthBuilder().build(googleAuthDTO);

        if(! googleAuthService.checkExistAuthToken(googleToken)) {
            return new ResponseEntity<>("Please, log in or sign up if you do not have an account", HttpStatus.UNAUTHORIZED);
        }
        //TODO
        //  AUTHENTICATION END HERE

        //DELETE TOKEN FROM TABLE GOOGLE TOKEN
        googleAuthService.logoutGoogleToken(googleToken);

        return new ResponseEntity<>("Log out successful",HttpStatus.OK);
    }
}
