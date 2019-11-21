package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.ClientService;
import com.desapp.grupoc1e022019.services.dtos.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "clientController")
public class ClientController {

    @Autowired
    private ClientService clientService = new ClientService();

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();


    //EL POST DEL CLIENT SE HACE EN EL SIGNUP DEL AUTH CONTROLLER

    @RequestMapping(method = RequestMethod.GET, value = "/client/{idClient}")
    public ResponseEntity getClient(@PathVariable long idClient) {
        //TODO
        // ACA VA EL ASPECTO DEL AUTH TOKEN DE GOOGLE

        if(! clientService.clientExist(idClient)){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client clientFound = clientService.getClient(idClient);

        return new ResponseEntity<>(clientFound, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/client/basicInfo")
    public ResponseEntity updateClientBasicInfo(@RequestBody ClientDTO clientDTO) {
        //TODO
        // ACA VA EL ASPECTO DEL AUTH TOKEN DE GOOGLE

        if(! clientService.clientExist(clientDTO.getId())){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.updateClientBasicInfo(clientDTO);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    //*TODO
    //   ESTO ES TEMPORAL PARA LA ENTREGA 2 */
    @RequestMapping(method = RequestMethod.POST, value = "/client/buy")
    public ResponseEntity buy(@RequestBody HashMap<String,String> body) {
        //TODO
        // ACA VA EL ASPECTO DEL AUTH TOKEN DE GOOGLE

        if(! clientService.clientExist(Long.parseLong(body.get("id")))){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.buy(Long.parseLong(body.get("id")),Double.parseDouble(body.get("price")));

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/client/accredit")
    public ResponseEntity accredit(@RequestBody HashMap<String,String> body) {
        //TODO
        // ACA VA EL ASPECTO DEL AUTH TOKEN DE GOOGLE

        if(! clientService.clientExist(Long.parseLong(body.get("id")))){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.accredit(Long.parseLong(body.get("id")),Double.parseDouble(body.get("amount")));

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }
}
