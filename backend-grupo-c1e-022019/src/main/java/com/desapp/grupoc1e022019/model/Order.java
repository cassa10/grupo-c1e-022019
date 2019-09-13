package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.OrderState.DeliveredOrder;
import com.desapp.grupoc1e022019.model.OrderState.OrderState;
import com.desapp.grupoc1e022019.model.OrderState.SendingOrder;

public class Order {
    private OrderState state;
    private ViendasYa.Average average;

    public Order(OrderState state,Double stars) {
        this.average = new ViendasYa.Average(stars);
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

    public Double getStars() {
        return average.getStars();
    }

    public void rate(Integer score) {
        average.rate(score);
    }
}
