package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class NormalClientState extends ClientState {

    @Override
    public boolean isNormal(){
        return true;
    }

    public void buyOrder(Order order){
        order.getClient().debit(new Credit(order.getOrderPrice()));
    }

    @Override
    public String toString(){
        return "NormalClient";
    }
}
