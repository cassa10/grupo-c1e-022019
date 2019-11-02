package com.desapp.grupoc1e022019.services.controllers;

import com.desapp.grupoc1e022019.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Scope(value = "session")
@Component(value = "orderController")
public class OrderController {

    @Autowired
    private OrderService providerService = new OrderService();
}
