package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.services.sender.SendEmailTLS;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class EmailSenderService {

    @Async("threadPoolTaskExecutor")
    public void sendOrderPendingEmail(Order order) {

        DecimalFormat df = new DecimalFormat("#.00");
        String clientName = order.getClient().getFirstName();
        String clientFullName = order.getClient().getFullName();

        SendEmailTLS.send(order.getClient().getEmail(),"Order is pending",
                "Hi "+ clientName +", Your order is pending. Yours '$"+df.format(order.getMenuInfoPrice())+"' credits was debited, please wait " +
                        "until today midnight when your order will be confirmed. If your order is cancelled, you will be accredit as soon as possible.");

        SendEmailTLS.send(order.getMenu().getProvider().getEmail(),"with Client: '"+ clientFullName+"' - Your menu "+ order.getMenu().getName() + " was ordered",
                "Hi, Congrats. Your menu has a new customer. If the order is confirmed, you will recieve in your balance account the following amount: " +
                        "'$"+df.format(order.getMenuInfoPrice())+"'. Thank you for being a good provider! ;D");
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

    @Async("threadPoolTaskExecutor")
    public void sendOrderFinalPriceHasChanged(Order order, Double prevOrderPricePerAmount, Double newOrderPricePerAmount, Double differenceCreditAccredited) {
        DecimalFormat df = new DecimalFormat("#.00");

        SendEmailTLS.send(order.getClient().getEmail(),
                "Your menu order: '" + order.getMenu().getName() +"'- Amount: '"+ order.getMenusAmount()+"' has achieve a menu discount",
                "Hi, your menu order '"+ order.getMenu().getName() + "' with amount '" +order.getMenusAmount() +"' has achieve a menu discount. "+
                        "Your order payment was $"+df.format(prevOrderPricePerAmount * order.getMenusAmount())+". " +
                        "Now, order final price is $"+ df.format(newOrderPricePerAmount * order.getMenusAmount()) +". "+
                        "And your balance account was accredited +$"+ df.format(differenceCreditAccredited) + ". "+
                        "Thanks for using our app! :D"
        );
    }

    @Async("threadPoolTaskExecutor")
    public void sendOrderIsConfirmed(Order order, Double newOrderPricePerAmount) {
        DecimalFormat df = new DecimalFormat("#.00");
        String finalPriceOrder = df.format(newOrderPricePerAmount * order.getMenusAmount());
        String clientName = order.getClient().getFirstName();
        String clientFullName = order.getClient().getFullName();

        SendEmailTLS.send(order.getClient().getEmail(),
                "Your menu order: '" + order.getMenu().getName() +"'- Amount: '"+ order.getMenusAmount()+"' - State: Confirmed",
                "Hi "+ clientName +", your menu order '"+ order.getMenu().getName() + "' with amount '" +order.getMenusAmount() +"' is confirmed. "+
                        "Order final price is $"+ finalPriceOrder +". "+
                "All order info is in your history order wall." + " Also, you can rank this order now, but wait to receive the" +
                        "order before! " +
                "Thanks for using our app and enjoy your order! :D"
        );

        SendEmailTLS.send(order.getMenu().getProvider().getEmail(),
                "Your menu: '" + order.getMenu().getName() +"' with Client: '" + clientFullName +"' has a confirmed order!",
                "Hi, your balance account accredited '$"+ finalPriceOrder + " by an confirmed order. "+
                        "This order request '"+ order.getMenu().getName() + "' x" + order.getMenusAmount() + " (amount). " +
                        "In your order history wall, you will have all info to make this order! "+
                        "Thank you for make our app great and keep going!"
        );

    }

    @Async("threadPoolTaskExecutor")
    public void sendClientOrderIsSending(Order order) {
        String customDeliverMessage = order.getDeliverType().customDeliverMessage(order);
        String clientName = order.getClient().getFirstName();

        SendEmailTLS.send(order.getClient().getEmail(),
                "Your menu order: '" + order.getMenu().getName() +"'- Amount: '"+ order.getMenusAmount()+"' - State: Sending",
                "Hi "+clientName+", your menu order '"+ order.getMenu().getName() + "' with amount '" +order.getMenusAmount() +"' is being sending. "+
                         customDeliverMessage + "All order info is in your history order wall. " +
                        "Thanks for using our app and enjoy your order! :D"
        );
    }

    @Async("threadPoolTaskExecutor")
    public void sendClientOrderWasDelivered(Order order) {
        String clientName = order.getClient().getFirstName();
        String clientFullName = order.getClient().getFullName();

        SendEmailTLS.send(order.getClient().getEmail(),
                "Your menu order: '" + order.getMenu().getName() +"'- Amount: '"+ order.getMenusAmount()+"' - State: Delivered",
                "Hi "+clientName+", your menu order '"+ order.getMenu().getName() + "' with amount '" +order.getMenusAmount() +"' was delivered. "+
                        "Please, rank this order if you do not! " +
                        "Thanks for using our app and enjoy your order! :D"
        );

        SendEmailTLS.send(order.getMenu().getProvider().getEmail(),
                "Your menu order: '" + order.getMenu().getName() +"'- Amount: '"+ order.getMenusAmount() +"' with Client: '" + clientFullName +"' - State: Delivered",
                "Hi, your menu order '"+ order.getMenu().getName() + "' with amount '" +order.getMenusAmount() +"' was delivered to client "+
                        "All order info is in your history order wall. " +
                        "Thank you for make our app great and keep going! ;)"
        );
    }
}
