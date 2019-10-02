package com.desapp.grupoc1e022019.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class APIController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity apiTest() {
        return new ResponseEntity<>("API TESTING", HttpStatus.OK);
    }
}
