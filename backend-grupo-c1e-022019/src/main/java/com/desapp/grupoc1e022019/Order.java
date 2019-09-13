package com.desapp.grupoc1e022019;

public class Order {
    private OrderState state;

    public Order(OrderState state) {

        this.state = state;
    }

    public OrderState getState() {
        return state;
    }

    public void delivered() {
        state = new DeliveredOrder();
    }

    public void sending() {
        state = new SendingOrder();
    }
}
