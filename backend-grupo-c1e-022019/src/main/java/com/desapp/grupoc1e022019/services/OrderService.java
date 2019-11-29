package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

    public boolean providerHasReachOrdersLimit(Menu menuRecovered, LocalDateTime deliverDate) {
        return orderDAO.sizeOfOrdersPerDayOfMenu(menuRecovered,deliverDate) >= menuRecovered.getMaxSalesPerDay();
    }

    public Long sizeOrdersPerDay(Menu menuRecovered, LocalDateTime deliverDate) {
        return orderDAO.sizeOfOrdersPerDayOfMenu(menuRecovered,deliverDate);
    }

    public List<Order> getHistoricProviderOrdersTaken(Provider providerRecovered) {
        return orderDAO.getHistoricProviderOrdersTaken(providerRecovered);
    }

    public List<Order> getHistoricClientOrders(Client clientRecovered) {
        return orderDAO.getHistoricClientOrders(clientRecovered);
    }

    @Transactional
    public Order cancelOrder(Order orderRecovered) {
        //Cancelled deposit client credit
        orderRecovered.cancelled();

        //Save client current balance
        clientDAO.save(orderRecovered.getClient());

        return orderDAO.save(orderRecovered);
    }

    public Optional<Order> findOrderById(long idOrder) {
        return orderDAO.findOrderById(idOrder);
    }
}
