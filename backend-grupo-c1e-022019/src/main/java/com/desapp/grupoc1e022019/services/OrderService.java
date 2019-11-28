package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Scope(value = "session")
@Component(value = "orderService")
public class OrderService {

    @Autowired
    private OrderDAO orderDAO = new OrderDAO();
    @Autowired
    private ClientDAO clientDAO = new ClientDAO();

    @Transactional
    public Order createOrder(Order newOrder) {

        Credit orderPrice = new Credit(newOrder.getOrderPrice());
        newOrder.getClient().debit(orderPrice);

        clientDAO.save(newOrder.getClient());

        return orderDAO.save(newOrder);
    }

}
