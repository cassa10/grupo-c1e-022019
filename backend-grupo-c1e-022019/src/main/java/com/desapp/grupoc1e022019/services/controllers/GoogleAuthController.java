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

import java.util.Optional;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "googleAuthController")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @Autowired
    private ClientService clientService = new ClientService();

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity loginAuth(@RequestBody GoogleAuthDTO googleAuthDTO) {

        Optional<Client> maybeClient = clientService.findClientByGoogleId(googleAuthDTO.getGoogleId());

        if(! maybeClient.isPresent()) {
            return new ResponseEntity<>("Your account does not exist", HttpStatus.BAD_REQUEST);
        }

        GoogleToken googleAuth = new GoogleAuthBuilder().build(googleAuthDTO);

        googleAuthService.saveOrUpdateGoogleToken(googleAuth);

        return new ResponseEntity<>(maybeClient.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signupAuth(@RequestBody ClientDTO clientDTO) {
        if(! clientDTO.getGoogleAuthDTO().getGoogleId().equals(clientDTO.getGoogleId())){
            return new ResponseEntity<>("Request with bad data", HttpStatus.BAD_REQUEST);
        }

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

        Client newClient = clientService.saveClientAndGoogleAuth(client,googleAuth);

        return new ResponseEntity<>(newClient, HttpStatus.OK);
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
