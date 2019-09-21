package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class PendingOrder extends OrderState {


    @Override
    public void update(Order order) {
        order.setState(new ConfirmedOrder());
    }
}
