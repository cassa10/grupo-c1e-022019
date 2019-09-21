package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class DeliveredOrder extends OrderState {
    @Override
    public void rate(Integer score, Order order) {
        order.score(score);
    }
}
