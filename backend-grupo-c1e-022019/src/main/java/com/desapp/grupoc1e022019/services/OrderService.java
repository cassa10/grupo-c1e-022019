package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.persistence.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "orderService")
public class OrderService {

    @Autowired
    private OrderDAO orderDAO = new OrderDAO();
}
