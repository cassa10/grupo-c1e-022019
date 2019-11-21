package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.DecimalFormat;

@Scope(value = "session")
@Component(value = "orderService")
public class OrderService {

    @Autowired
    private OrderDAO orderDAO = new OrderDAO();
    @Autowired
    private ClientDAO clientDAO = new ClientDAO();

    @Transactional
    public Order createOrder(Order newOrder) {
        //SE ACREDITA EN PROVIDER CUANDO

        Credit orderPrice = new Credit(newOrder.getOrderPrice());
        newOrder.getClient().debit(orderPrice);

        clientDAO.save(newOrder.getClient());
        Order orderSaved = orderDAO.save(newOrder);

        DecimalFormat df = new DecimalFormat("#.00");

        SendEmailTLS.send(newOrder.getClient().getEmail(),"Order is pending",
                "Hi, Your order is pending. Yours '"+df.format(orderPrice.getAmount())+"' credits was debited, please wait " +
                        "until today midnight when your order will be confirmed. If your order is cancelled, you will be accredit as soon as possible.");

        SendEmailTLS.send(newOrder.getMenu().getProvider().getEmail(),"Your menu "+ newOrder.getMenu().getName() + " was ordered",
                "Hi, Congrats. Your menu has a new customer. If the order is confirmed, you will recieve in your balance account the following amount: " +
                        "'"+df.format(orderPrice.getAmount())+"'. Thank you for being a good provider! ;D");

        return orderSaved;
    }
}
