package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
import com.desapp.grupoc1e022019.services.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@CrossOrigin
@RestController
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/provider")
    public ResponseEntity createEntity(@RequestBody ProviderDTO providerDTO) {
        Provider newProvider = ProviderBuilder.aProvider()
                .withName(providerDTO.getName())
                .withCredit(new Credit(0d))
                .withProviderState(new NormalProvider())
                .withDescription(providerDTO.getDescription())
                .withStrikesMenu(0)
                .withSchedule(new Schedule(new HashMap<DayOfWeek, Set<BussinessTime>>()))
                .withAddress(providerDTO.getAddress())
                .withCity(providerDTO.getCity())
                .withDeliveryMaxDistanceInKM(providerDTO.getDeliveryMaxDistanceInKM())
                .withEmail(providerDTO.getEmail())
                .withLogo(providerDTO.getLogo())
                .withTelNumber(providerDTO.getTelNumber())
                .withWebUrl(providerDTO.getWebURL())
                .withMenus(new ArrayList<>())
                .build();
        providerRepository.save(newProvider);

        return new ResponseEntity<>(newProvider, HttpStatus.OK);
    }
}
