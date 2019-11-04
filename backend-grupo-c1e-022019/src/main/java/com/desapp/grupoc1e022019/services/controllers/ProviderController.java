package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
import com.desapp.grupoc1e022019.services.dtos.WithdrawCreditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "providerController")
public class ProviderController {

    @Autowired
    private ProviderService providerService = new ProviderService();

    @RequestMapping(method = RequestMethod.POST, value = "/provider")
    public ResponseEntity createProvider(@RequestBody ProviderDTO providerDTO) {

        if(providerService.existProviderWithSameEmail(providerDTO.getEmail())){
            return new ResponseEntity<>("Provider already exists, please sign on with different email",HttpStatus.BAD_REQUEST);
        }

        Provider newProvider = ProviderBuilder.aProvider()
                .withName(providerDTO.getName())
                .withCredit(new Credit())
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
                .build();

        providerService.save(newProvider);

        return new ResponseEntity<>(newProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/provider/delete/{idProvider}")
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

    @RequestMapping(method = RequestMethod.PUT, value ="/provider/basicInfo")
    public ResponseEntity updateProviderBasicInfo(@RequestBody ProviderDTO providerDTO) {

        if(! providerService.providerExists(providerDTO.getId())){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        Provider updatedProvider = providerService.updateProviderBasicInfo(providerDTO);

        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/provider/credit/withdraw")
    public ResponseEntity withdrawCredit(@RequestBody WithdrawCreditDTO withdrawCreditDTO) {

        if(withdrawCreditDTO.getAmountToWithdraw() <= 0){
            return new ResponseEntity<>("Invalid credit amount to withdraw",HttpStatus.BAD_REQUEST);
        }
        if(! providerService.providerExists(withdrawCreditDTO.getIdProvider())){
            return new ResponseEntity<>("Provider does not exist",HttpStatus.NOT_FOUND);
        }

        Credit creditToWithdraw = new Credit(withdrawCreditDTO.getAmountToWithdraw());

        try {
            providerService.withdrawCredit(withdrawCreditDTO.getIdProvider(), creditToWithdraw);
        }catch (InsufficientCreditException e){
            return new ResponseEntity<>("Insufficient founds, withdraw cancelled",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("Withdraw accepted",HttpStatus.OK);
    }

}
