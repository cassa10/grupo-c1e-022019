package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.services.GoogleAuthService;
import com.desapp.grupoc1e022019.services.ProviderService;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
import com.desapp.grupoc1e022019.services.dtos.ProviderPublicDataDTO;
import com.desapp.grupoc1e022019.services.dtos.ScheduleDTO;
import com.desapp.grupoc1e022019.services.dtos.WithdrawCreditDTO;
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
@Component(value = "providerController")
public class ProviderController {

    @Autowired
    private ProviderService providerService = new ProviderService();

    @Autowired
    private GoogleAuthService googleAuthService = new GoogleAuthService();

    @RequestMapping(method = RequestMethod.GET, value = "/exist_provider")
    public ResponseEntity isAProvider(@RequestParam HashMap<String,String> body) {
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        if(! googleAuthService.clientHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        boolean value = providerService.existProviderByGoogleId(googleId);

        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/provider")
    public ResponseEntity createProvider(@RequestBody ProviderDTO providerDTO) {

        if(! googleAuthService.clientHasAccess(providerDTO.getGoogleId(),providerDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> mayBeProvider = providerService.findProviderByGoogleId(providerDTO.getGoogleId());

        if(mayBeProvider.isPresent()){
            return new ResponseEntity<>("You are already a provider",HttpStatus.BAD_REQUEST);
        }

        providerDTO.trimAllStringInputs();

        if(! providerDTO.formPostProviderIsOk()){
           return new ResponseEntity<>("Invalid data form",HttpStatus.BAD_REQUEST);
        }

        //SE INICIALIZA CON UN SCHEDULE VACIO Y DESPUES LO AGREGARA MAS ADELANTE

        Provider newProvider = ProviderBuilder.aProvider()
                .withGoogleId(providerDTO.getGoogleId())
                .withName(providerDTO.getName())
                .withCredit(new Credit())
                .withProviderState(new NormalProvider())
                .withDescription(providerDTO.getDescription())
                .withStrikesMenu(0)
                .withSchedule(new Schedule())
                .withAddress(providerDTO.getAddress())
                .withCity(providerDTO.getCity())
                .withDeliveryMaxDistanceInKM(providerDTO.getDeliveryMaxDistanceInKM())
                .withEmail(providerDTO.getEmail())
                .withLogo(providerDTO.getLogo())
                .withTelNumber(providerDTO.getTelNumber())
                .withWebUrl(providerDTO.getWebURL())
                .build();

        newProvider = providerService.save(newProvider);

        return new ResponseEntity<>(newProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/provider")
    public ResponseEntity getProvider(@RequestParam HashMap<String,String> body) {
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        if(! googleAuthService.providerHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> mayBeProvider = providerService.findProviderByGoogleId(googleId);

        if(! mayBeProvider.isPresent()){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if(mayBeProvider.get().isPenalized()){
            return new ResponseEntity<>("You are a provider penalized", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(mayBeProvider.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/provider/basicInfo")
    public ResponseEntity updateProviderBasicInfo(@RequestBody ProviderDTO providerDTO) {

        if(! googleAuthService.providerHasAccess(providerDTO.getGoogleId(),providerDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> mayBeProvider = providerService.findProviderByGoogleId(providerDTO.getGoogleId());

        if(! mayBeProvider.isPresent()){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        // Aplica trim() a todos los strins menos el email que no esta presente en este post
        providerDTO.trimAllStringBasicInfo();

        if(! providerDTO.isValidBasicInfo()) {
            return new ResponseEntity<>("Bad request data", HttpStatus.BAD_REQUEST);
        }

        Provider updatedProvider = providerService.updateProviderBasicInfo(mayBeProvider.get(), providerDTO);

        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/provider/credit/withdraw")
    public ResponseEntity withdrawCredit(@RequestBody WithdrawCreditDTO withdrawCreditDTO) {

        if(! googleAuthService.providerHasAccess(withdrawCreditDTO.getGoogleId(),withdrawCreditDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        if(withdrawCreditDTO.getAmountToWithdraw() <= 0){
            return new ResponseEntity<>("Invalid credit amount to withdraw",HttpStatus.BAD_REQUEST);
        }

        Optional<Provider> mayBeProvider = providerService.findProviderByGoogleId(withdrawCreditDTO.getGoogleId());

        if(! mayBeProvider.isPresent()){
            return new ResponseEntity<>("Provider does not exist",HttpStatus.NOT_FOUND);
        }

        Credit creditToWithdraw = new Credit(withdrawCreditDTO.getAmountToWithdraw());

        try {
            Provider providerRecovered = providerService.withdrawCredit(mayBeProvider.get(), creditToWithdraw);
            return new ResponseEntity<>(providerRecovered,HttpStatus.OK);
        }catch (InsufficientCreditException e){
            return new ResponseEntity<>("Insufficient founds, withdraw cancelled",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value ="/provider/schedule")
    public ResponseEntity updateProviderSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        if(! googleAuthService.providerHasAccess(scheduleDTO.getGoogleId(),scheduleDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> mayBeProvider = providerService.findProviderByGoogleId(scheduleDTO.getGoogleId());

        if(! mayBeProvider.isPresent()){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if(! scheduleDTO.isValidSchedule()){
            return new ResponseEntity<>("Bad request data", HttpStatus.BAD_REQUEST);
        }

        Provider updatedProvider = providerService.updateProviderSchedule(mayBeProvider.get(), scheduleDTO);

        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value ="/provider/delete")
    public ResponseEntity deleteProvider(@RequestBody ProviderDTO providerDTO) {

        if(! googleAuthService.providerHasAccess(providerDTO.getGoogleId(),providerDTO.getTokenAccess())){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        Optional<Provider> maybeProviderRecovered = providerService.findProviderByGoogleId(providerDTO.getGoogleId());

        if(! maybeProviderRecovered.isPresent()){
            return new ResponseEntity<>("Provider does not exist", HttpStatus.NOT_FOUND);
        }

        if(maybeProviderRecovered.get().isPenalized()){
            return new ResponseEntity<>("You cannot be deleted because you are penalized", HttpStatus.NOT_ACCEPTABLE);
        }

        if(maybeProviderRecovered.get().getStrikesMenu() > 0){
            return new ResponseEntity<>("You cannot be deleted because you have 1 or more strikes", HttpStatus.NOT_ACCEPTABLE);
        }

        //  CREATE A SAFE DELETE (CANCEL ALL MENUS OF PROVIDER)
        //  AND SET A PROVIDER DELETE STATE WHEN SCHEDULE(00hs) RUNS, DELETE PROVIDER WITH ALL HIS MENUS
        Provider providerInDeletingState = providerService.setProviderDeletingProcess(maybeProviderRecovered.get());

        return new ResponseEntity<>(providerInDeletingState, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/provider/public")
    public ResponseEntity clientGetMenuProvider(@RequestParam HashMap<String,String> body){
        String googleId = body.get("googleId");
        String tokenAccess = body.get("tokenAccess");
        long providerId;

        if(! googleAuthService.clientHasAccess(googleId,tokenAccess)){
            return new ResponseEntity<>("Please, log in", HttpStatus.UNAUTHORIZED);
        }

        try{
            providerId = Long.parseLong(body.get("providerId"));
        }catch (Exception e){
            return new ResponseEntity<>("Bad data request",HttpStatus.BAD_REQUEST);
        }

        Optional<Provider> maybeProvider = providerService.findProviderById(providerId);

        if(! maybeProvider.isPresent()){
            return new ResponseEntity<>("Provider not found",HttpStatus.NOT_FOUND);
        }

        double providerRank = providerService.getProviderRankReputation(maybeProvider.get());
        ProviderPublicDataDTO providerPublicDataDTO = new ProviderPublicDataDTO(maybeProvider.get(),providerRank);

        return new ResponseEntity<>(providerPublicDataDTO,HttpStatus.OK);
    }

}
