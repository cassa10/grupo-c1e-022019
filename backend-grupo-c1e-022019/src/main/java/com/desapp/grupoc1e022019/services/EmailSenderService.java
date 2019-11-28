package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.services.sender.SendEmailTLS;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class EmailSenderService {

    @Async("threadPoolTaskExecutor")
    public void sendOrderPendingEmail(Order orderSaved) {

        DecimalFormat df = new DecimalFormat("#.00");

        SendEmailTLS.send(orderSaved.getClient().getEmail(),"Order is pending",
                "Hi, Your order is pending. Yours '$"+df.format(orderSaved.getMenuInfoPrice())+"' credits was debited, please wait " +
                        "until today midnight when your order will be confirmed. If your order is cancelled, you will be accredit as soon as possible.");

        SendEmailTLS.send(orderSaved.getMenu().getProvider().getEmail(),"Your menu "+ orderSaved.getMenu().getName() + " was ordered",
                "Hi, Congrats. Your menu has a new customer. If the order is confirmed, you will recieve in your balance account the following amount: " +
                        "'$"+df.format(orderSaved.getMenuInfoPrice())+"'. Thank you for being a good provider! ;D");
    }
}
