package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.services.EntityPersistencePilot;
import com.desapp.grupoc1e022019.persistence.EntityPersistencePilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class APIController {
    @Autowired
    private EntityPersistencePilotRepository entityPersistencePilotRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity apiTest() {
        return new ResponseEntity<>("API TESTING", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity createEntity() {
        entityPersistencePilotRepository.save(new EntityPersistencePilot(10L,"nico"));

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
