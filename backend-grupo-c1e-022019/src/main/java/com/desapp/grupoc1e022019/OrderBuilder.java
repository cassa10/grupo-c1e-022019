package com.desapp.grupoc1e022019;

public class OrderBuilder {
    private OrderState state = new PendingOrder();

    public static OrderBuilder anOrder(){
        return new OrderBuilder();
    }

    public Order build() {
        return new Order(state);
    }
}
