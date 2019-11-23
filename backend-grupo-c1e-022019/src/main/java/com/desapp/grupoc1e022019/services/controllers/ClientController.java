package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.services.ClientService;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.dtos.AccreditDTO;
import com.desapp.grupoc1e022019.services.dtos.ClientDTO;
import com.desapp.grupoc1e022019.services.dtos.GoogleAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "clientController")
public class ClientController {

    @Autowired
    private ClientService clientService = new ClientService();

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @RequestMapping(method = RequestMethod.GET, value = "/client")
    public ResponseEntity getClient(@RequestParam HashMap<String,String> body) {
        String idGoogle = body.get("googleId");
        String accessToken = body.get("accessToken");

        if(! googleAuthService.clientHasAccess(idGoogle,accessToken)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Client> maybeClient = clientService.findClientByGoogleId(idGoogle);
        if(! maybeClient.isPresent()){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(maybeClient.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/client/basicInfo")
    public ResponseEntity updateClientBasicInfo(@RequestBody ClientDTO clientDTO) {
        GoogleAuthDTO googleAuthDTO = clientDTO.getGoogleAuthDTO();
        if(! googleAuthService.clientHasAccess(googleAuthDTO.getGoogleId(),googleAuthDTO.getAccessToken())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }
        Optional<Client> maybeClient = clientService.findClientById(clientDTO.getId());

        if(! maybeClient.isPresent()){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.updateClientBasicInfo(maybeClient.get(),clientDTO);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    //*TODO
    //   ESTO ES TEMPORAL PARA LA ENTREGA 2 */
    @RequestMapping(method = RequestMethod.POST, value = "/client/buy")
    public ResponseEntity buy(@RequestBody HashMap<String,String> body) {

        if(! clientService.clientExist(Long.parseLong(body.get("id")))){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.buy(Long.parseLong(body.get("id")),Double.parseDouble(body.get("price")));

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/client/accredit")
    public ResponseEntity accredit(@RequestBody AccreditDTO accreditDTO) {

        if(accreditDTO.getAmount() == null || accreditDTO.getAmount() <= 0){
            return new ResponseEntity<>("Request with bad data", HttpStatus.BAD_REQUEST);
        }

        if(! clientService.clientExist(accreditDTO.getId())){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }

        Client updatedClient = clientService.accredit(accreditDTO.getId(),accreditDTO.getAmount());

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }
}
