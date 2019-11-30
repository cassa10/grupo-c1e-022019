package com.desapp.grupoc1e022019.services;


import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.GoogleTokenDAO;
import com.desapp.grupoc1e022019.persistence.OrderDAO;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
import com.desapp.grupoc1e022019.services.logger.ServiceLogger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleService {

    private static Logger logger = Logger.getLogger(ServiceLogger.class);

    @Autowired
    private OrderDAO orderDAO = new OrderDAO();
    @Autowired
    private ClientDAO clientDAO = new ClientDAO();
    @Autowired
    private ProviderDAO providerDAO = new ProviderDAO();
    @Autowired
    private EmailSenderService emailSenderService = new EmailSenderService();

    @Autowired
    private GoogleTokenDAO googleTokenDAO = new GoogleTokenDAO();

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void scheduleConfirmPendingOrdersAndCheckoutPayments() {
        // "0 0 0 * * *" -- cron: Every day 00hs.
        // "s m h d m y" -- cron code

       logger.info("Schedule task 'Confirm pending orders and checkout payments' has started. ");

        LocalDateTime dateTime = LocalDateTime.now().withSecond(0);

        List<Order> ordersToConfirm = orderDAO.findAllByPendingStateAndDeliverDateBetweenAndSortedByIdMenu(dateTime,dateTime.plusDays(2));

        logger.info("List of orders to confirm: " +
                ordersToConfirm.stream().map(o-> "{ IdOrder: " + o.getId()+", idMenu:"+o.getIdMenu()+ ", orderAmount: "
                + o.getMenusAmount() + ", deliverDate: " + o.getDeliverDate() + ", menuInfoPrice: "+ o.getMenuInfoPrice() + " }" ).collect(Collectors.joining()));

        long idMenu;
        long totalMenuAmount;

        if(!ordersToConfirm.isEmpty()){
            idMenu = ordersToConfirm.get(0).getIdMenu();
            totalMenuAmount = orderDAO.countTotalAmountMenusByPendingStateAndIdMenuAndDeliverDateBetween(
                    idMenu,dateTime,dateTime.plusDays(2));

            for (Order order : ordersToConfirm){

                if(idMenu != order.getIdMenu()) {
                    idMenu = order.getIdMenu();
                    totalMenuAmount = orderDAO.countTotalAmountMenusByPendingStateAndIdMenuAndDeliverDateBetween(
                            idMenu,dateTime,dateTime.plusDays(2));
                }

                    this.confirmOrderAndCalculateFinalPriceAndSaveEntitiesAndSendEmails(order,totalMenuAmount);
            }

        }else{
            logger.info("No orders in Peding state and deliver date between ["+dateTime+ ", "+ dateTime.plusDays(2) +"]");
        }


        logger.info(
                LocalDateTime.now() +" - Schedule task 'Confirm pending orders and checkout payments' has finished. "
        );

    }

    private void confirmOrderAndCalculateFinalPriceAndSaveEntitiesAndSendEmails(Order order, Long totalMenuAmount) {
        Double prevOrderPricePerAmount = order.getMenuInfoPrice() / order.getMenusAmount();

        Double newOrderPricePerAmount = order.getMenuInfo().getMenuPriceWithAmount(totalMenuAmount.intValue()) / totalMenuAmount;

        order.confirmed();

        order.setFinalOrderPrice(newOrderPricePerAmount);

        if(! prevOrderPricePerAmount.equals(newOrderPricePerAmount)){
            double differenceCredit = (prevOrderPricePerAmount - newOrderPricePerAmount) * order.getMenusAmount();
            order.getClient().deposit(new Credit(differenceCredit));
            clientDAO.save(order.getClient());
            emailSenderService.sendOrderFinalPriceHasChanged(order,prevOrderPricePerAmount,newOrderPricePerAmount, differenceCredit);
        }

        order.payToProvider();
        providerDAO.save(order.getMenu().getProvider());
        orderDAO.save(order);

        emailSenderService.sendOrderIsConfirmed(order, newOrderPricePerAmount);
    }

    @Scheduled(cron = "0 30 * * * *")
    @Async("threadPoolTaskExecutor")
    @Transactional
    public void expireOldTokens(){
        //"0 30 * * * *" - cron: Every hour in minute 30.

        logger.info("Schedule task 'Expire old tokens' has started. ");

        Integer tokensExpired = googleTokenDAO.deleteAllAuthTokenExpired();
        logger.info("Number of tokens are expired: " + tokensExpired);

        logger.info("Schedule task 'Expire old tokens' has finished. ");

    }
}
