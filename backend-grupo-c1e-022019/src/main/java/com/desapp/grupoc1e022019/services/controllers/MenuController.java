package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.services.MenuService;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.dtos.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "menuController")
public class MenuController {

    @Autowired
    private MenuService menuService = new MenuService();

    @Autowired
    private ProviderService providerService = new ProviderService();

    @RequestMapping(method = RequestMethod.POST, value = "/menu")
    public ResponseEntity postMenu(@RequestBody MenuDTO menuDTO){

        if(! providerService.providerExists(menuDTO.getIdProvider())){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if( menuService.existsMenuWithSameName(menuDTO.getName())){
            return new ResponseEntity<>("Please, choose a different name", HttpStatus.ALREADY_REPORTED);
        }

        Provider provider = providerService.getProvider(menuDTO.getIdProvider());
        Menu newMenu = MenuBuilder.aMenu()
                .withProvider(provider)
                .withName(menuDTO.getName())
                .withDescription(menuDTO.getDescription())
                .withCategories(menuDTO.getCategories())
                .withDeliveryValue(menuDTO.getDeliveryValue())
                .withEffectiveDate(menuDTO.getEffectiveDate())
                .withAverageDeliveryTimeInMinutes(menuDTO.getAverageDeliveryTimeInMinutes())
                .withMaxSalesPerDay(menuDTO.getMaxSalesPerDay())
                .build();

        menuService.saveMenu(newMenu);

        return new ResponseEntity<>(newMenu, HttpStatus.OK);
    }

}
