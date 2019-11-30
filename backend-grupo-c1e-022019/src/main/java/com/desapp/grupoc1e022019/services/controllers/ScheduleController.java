package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "ScheduleController")
public class ScheduleController {


    @Autowired
    private ScheduleService scheduleService = new ScheduleService();

    @RequestMapping(method = RequestMethod.GET, value = "/schedule/tokens/expire/{password}")
    public ResponseEntity expiresAllExpiredTokens(@PathVariable String password) {


        if(password == null || ! password.equals("***password***")){
            return new ResponseEntity<>("You do not have access", HttpStatus.UNAUTHORIZED);
        }

        scheduleService.expireOldTokens();

        return new ResponseEntity<>("Method activated", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schedule/order/confirm/{password}")
    public ResponseEntity confirmPendingOrdersAndCheckoutPayments(@PathVariable String password) {

        if(password == null || ! password.equals("***password***")){
            return new ResponseEntity<>("You do not have access", HttpStatus.UNAUTHORIZED);
        }

        scheduleService.scheduleConfirmPendingOrdersAndCheckoutPayments();

        return new ResponseEntity<>("Method activated", HttpStatus.OK);
    }

}
