package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.clientState.NormalClient;
import com.desapp.grupoc1e022019.services.ClientService;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.dtos.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "clientController")
public class ClientController {

    @Autowired
    private ClientService clientService = new ClientService();

    @RequestMapping(method = RequestMethod.POST, value = "/client")
    public ResponseEntity createClient(@RequestBody ClientDTO clientDTO) {

        if(clientService.existSameClientEmail(clientDTO.getEmail())){
            return new ResponseEntity<>("Email is used, please use another email",HttpStatus.BAD_REQUEST);
        }

        Client client = ClientBuilder.aClient()
                        .withFirstName(clientDTO.getFirstName())
                        .withLastName(clientDTO.getLastName())
                        .withStateClient(new NormalClient())
                        .withEmail(clientDTO.getEmail())
                        .withCredit(new Credit())
                        .build();

        clientService.save(client);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/client/{idClient}")
    public ResponseEntity getClient(@PathVariable long idClient) {

        if(! clientService.clientExist(idClient)){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client clientFound = clientService.getClient(idClient);

        return new ResponseEntity<>(clientFound, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/client/basicInfo")
    public ResponseEntity updateClientBasicInfo(@RequestBody ClientDTO clientDTO) {

        if(! clientService.clientExist(clientDTO.getId())){
            return new ResponseEntity<>("Client does not exist", HttpStatus.NOT_FOUND);
        }
        Client updatedClient = clientService.updateClientBasicInfo(clientDTO);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }
}
