package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class DeliveredOrder extends OrderState {

    @Override
    public boolean isStateDelivered(){return true;}

    @Override
    public void rate(Integer score, Order order) {
        order.setStars(score);
        order.getClient().orderRanked(order);
        order.getMenu().addRate(score);
        order.setState(new RankedOrder());

        //TODO
        // USAR EL SERVICE EN LA PROXIMA ENTREGA
    }

    @Override
    public String toString(){
        return "DELIVERED";
    }

}
