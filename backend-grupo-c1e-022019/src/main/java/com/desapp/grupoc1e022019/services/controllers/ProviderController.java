package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBussinessTime;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "providerController")
public class ProviderController {

    @Autowired
    private ProviderService providerService = new ProviderService();

    @RequestMapping(method = RequestMethod.POST, value = "/provider")
    public ResponseEntity createProvider(@RequestBody ProviderDTO providerDTO) {

        if(providerService.existsProviderWithSameName(providerDTO.getName())){
            return new ResponseEntity("Please,choose a different name", HttpStatus.NOT_ACCEPTABLE);
        }
        Provider newProvider = ProviderBuilder.aProvider()
                .withName(providerDTO.getName())
                .withCredit(new Credit(0d))
                .withProviderState(new NormalProvider())
                .withDescription(providerDTO.getDescription())
                .withStrikesMenu(0)
                .withSchedule(providerDTO.getSchedule())
                .withAddress(providerDTO.getAddress())
                .withCity(providerDTO.getCity())
                .withDeliveryMaxDistanceInKM(providerDTO.getDeliveryMaxDistanceInKM())
                .withEmail(providerDTO.getEmail())
                .withLogo(providerDTO.getLogo())
                .withTelNumber(providerDTO.getTelNumber())
                .withWebUrl(providerDTO.getWebURL())
                .withMenus(new ArrayList<>())
                .build();

        providerService.save(newProvider);

        return new ResponseEntity<>(newProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value ="/provider/{idProvider}")
        public ResponseEntity deleteProvider(@PathVariable long idProvider) {

        if(! providerService.providerExists(idProvider)){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }
        providerService.delete(idProvider);

        return new ResponseEntity<>("Provider has been removed", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/provider/{idProvider}")
    public ResponseEntity getProvider(@PathVariable long idProvider) {

        if(! providerService.providerExists(idProvider)){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }
        Provider providerFound = providerService.getProvider(idProvider);

        return new ResponseEntity<>(providerFound, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value ="/provider")
    public ResponseEntity updateProvider(@RequestBody ProviderDTO updatedProvider) {

        if(! providerService.providerExists(updatedProvider.getId())){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }
        providerService.updateProvider(updatedProvider);

        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }
}
