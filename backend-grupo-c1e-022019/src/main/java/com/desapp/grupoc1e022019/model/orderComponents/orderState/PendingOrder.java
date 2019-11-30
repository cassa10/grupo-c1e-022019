package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class PendingOrder extends OrderState {

    @Override
    public boolean isStatePending(){return true;}

    @Override
    public void cancelled(Order order){
        order.setOrderState(new CancelledOrder());
        order.getClient().deposit(new Credit(order.getMenuInfoPrice()));
    }

    @Override
    public void confirm(Order order) {
        order.setOrderState(new ConfirmedOrder());
        order.getClient().haveToRankOrder();
    }

    @Override
    public String toString(){
        return "PendingOrder";
    }
}
