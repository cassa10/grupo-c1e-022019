package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class DeliveredOrder extends OrderState {

    @Override
    public boolean isStateDelivered(){return true;}

    @Override
    public void rate(Integer score, Order order) {
        order.setStars(score);
        order.getClient().orderRanked(order);
        order.setState(new RankedOrder());
        //TODO
        // USAR EL SERVICE EN LA PROXIMA ENTREGA
    }

}
