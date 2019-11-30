package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.Provider;
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

    @Async("threadPoolTaskExecutor")
    public void sendProviderMenuIsCancelledByBadAverageRankAndOwnAStrike(Menu menu) {
        SendEmailTLS.send(menu.getProvider().getEmail(),
                "Your menu: '" + menu.getName() +"' is cancelled by bad reputation - Your Strikes: "+menu.getProvider().getStrikesMenu()+"/10",
                "Bad news, your menu is cancelled by bad raking (Rank average do not stay up to 2 stars). " +
                        "Also, you add a strike in your provider account. If you reach up to 10 strikes, we will ban your provider account. So,"+
                        " try to get well with your clients and upgrade your menus presentations." +
                        " You own '" + menu.getProvider().getStrikesMenu() +"' strikes. Please, take care about this and good luck!"
                );
    }

    @Async("threadPoolTaskExecutor")
    public void sendProviderMenuIsCancelledAndHeIsPenalized(Menu menu) {
        SendEmailTLS.send(menu.getProvider().getEmail(),"Your menu: '" + menu.getName() +"' is cancelled - State: 'Penalized'",
                "Bad news, your menu is cancelled by bad raking (Rank average do not stay up to 2 stars). " +
                        "Also, you get up to 10 strikes, which is the limit to own. So, " +
                        " we are afraid to tell you of your provider account is banned by undefined time. " +
                        "Sorry but you wasted your 10 chances :("
        );
    }
}
