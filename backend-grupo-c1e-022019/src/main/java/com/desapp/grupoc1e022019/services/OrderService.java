package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.MenuDAO;
import com.desapp.grupoc1e022019.persistence.OrderDAO;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
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
    @Autowired
    private ProviderDAO providerDAO = new ProviderDAO();
    @Autowired
    private MenuDAO menuDAO = new MenuDAO();
    @Autowired
    private EmailSenderService emailSenderService = new EmailSenderService();

    @Transactional
    public Order createOrder(Order newOrder) {

        Credit orderPrice = new Credit(newOrder.getOrderPrice());
        newOrder.getClient().debit(orderPrice);

        clientDAO.save(newOrder.getClient());

        //ASYNC METHOD (DO NOT AFFECT TRANSACTIONALLY)
        emailSenderService.sendOrderPendingEmail(newOrder);

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

    @Transactional
    public Order rateOrder(Order orderRecovered, int rate) {

        //This method also remove order in client orders have to rank
        //And add rate in menu rank
        //And apply logic model (Cancel menu, add strike or penalized in provider)
        orderRecovered.rate(rate);

        //Save updated client
        clientDAO.save(orderRecovered.getClient());

        //Save updated menu
        menuDAO.save(orderRecovered.getMenu());

        //Save provider only if menu was cancelled by rate and his strikes was updated or his state
        if(orderRecovered.getMenu().isCancelledState()){
            providerDAO.save(orderRecovered.getMenu().getProvider());
        }

        return orderDAO.save(orderRecovered);
    }
}
