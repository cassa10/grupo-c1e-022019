package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class DeliveredOrder extends OrderState {

    @Override
    public boolean isStateDelivered(){return true;}

    @Override
    public void rate(Integer score, Order order) {
        order.setStars(score);
        order.getClient().orderRanked();
        order.getMenu().addRate(score);
    }

    @Override
    public String toString(){
        return "DeliveredOrder";
    }

    @Override
    public boolean isCanBeRated(Order order){

        return ! super.isRated(order.getStars());
    }
}
