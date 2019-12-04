package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.services.ScheduleService;
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
@Component(value = "ScheduleController")
public class ScheduleController {


    @Autowired
    private ScheduleService scheduleService = new ScheduleService();

    @RequestMapping(method = RequestMethod.GET, value = "/schedule/tokens/expire")
    public ResponseEntity expiresAllExpiredTokens(@RequestParam HashMap<String,String> body) {

        String password = body.get("password");

        if(password == null || ! password.equals("***password***")){
            return new ResponseEntity<>("You do not have access", HttpStatus.UNAUTHORIZED);
        }

        scheduleService.expireOldTokens();

        return new ResponseEntity<>("All old tokens has been expired successfully!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schedule/order/confirm")
    public ResponseEntity confirmPendingOrdersAndCheckoutPayments(@RequestParam HashMap<String,String> body) {

        String password = body.get("password");

        if(password == null || ! password.equals("***password***")){
            return new ResponseEntity<>("You do not have access", HttpStatus.UNAUTHORIZED);
        }

        scheduleService.scheduleConfirmPendingOrdersAndCheckoutPayments();

        return new ResponseEntity<>("All orders [now, now + 48hs] has been confirmed! ", HttpStatus.OK);
    }

}
