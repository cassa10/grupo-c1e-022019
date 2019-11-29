package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class ConfirmedOrder extends OrderState {

    @Override
    public boolean isStateConfirmed(){return true;}

    @Override
    public void rate(Integer score, Order order) {
        order.setStars(score);
        order.getClient().orderRanked(order);
        order.getMenu().addRate(score);
    }

    @Override
    public void sending(Order order){
        order.setOrderState(new SendingOrder());
    }

    @Override
    public String toString(){
        return "ConfirmedOrder";
    }

}
