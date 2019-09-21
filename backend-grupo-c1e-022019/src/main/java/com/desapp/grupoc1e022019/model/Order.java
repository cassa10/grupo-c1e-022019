package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.observer.Observer;
import com.desapp.grupoc1e022019.model.orderState.*;

public class Order implements Observer {
    private OrderState state;
    private ViendasYa.Average average;
    //TODO
    //  private Map<Menu,Integer>; (Menu, cantidad)
    //  private DeliverType; (Tipo de Entrega) Domicilio o Retiro
    //  private DeliverDate; (Fecha de Entrega)
    //  private DeliverHour; (Hora de Entrega) Dar una franja dependiendo de los menus y el max promedio de ellos(12:30 - 13:00)


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

    public void score(Integer score) {
        average.rate(score);
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public void update() {
        state.update(this);
    }

    public void cancelled() {
        state = new CancelledOrder();
    }

    public void rate(Integer score) {
        state.rate(score,this);
    }

    public void confirmed() {
        state = new ConfirmedOrder();
    }
}
