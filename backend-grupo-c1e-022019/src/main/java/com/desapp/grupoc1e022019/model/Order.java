package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.observer.Observer;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.OrderState;

public class Order implements Observer {
    private OrderState state;
    private Integer stars;
    private Client client;
    private Menu menu;
    private Integer menusAmount;
    private DeliverType deliverType;
    //TODO
    // EN CONTROLLER ORDER: NO SE PODRIA CREAR LA ORDEN SI EL MENU A ORDENAR ESTA CANCELADO

    public Order(OrderState state, Integer stars, Client client, Menu menu, Integer menusAmount, DeliverType deliverType) {
        this.stars = stars;
        this.state = state;
        this.client = client;
        this.menu = menu;
        this.menusAmount = menusAmount;
        this.deliverType = deliverType;

    }

    public Integer getStars() {
        return this.stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public void update() {
        //SI EL ESTADO ES PENDIENTE SE CONFIRMA SI SON LAS 00hs
        state.update(this);
    }

    public void confirmed(){
        this.update();
    }

    public void cancelled() {
        state.cancelled(this) ;
    }

    public void delivered() {
        state.delivered(this);
    }

    public void sending() {
        this.state.sending(this);
    }

    public void rate(Integer score) {
        state.rate(score,this);
    }

    public Client getClient(){
        return this.client;
    }

    public Menu getMenu(){ return this.menu;}

    public boolean isStateCancelled(){return this.state.isStateCancelled();}

    public boolean isStateConfirmed(){return this.state.isStateConfirmed();}

    public boolean isStateDelivered(){return this.state.isStateDelivered();}

    public boolean isStatePending(){return this.state.isStatePending();}

    public boolean isStateSending(){return this.state.isStateSending();}

    public boolean isStateRanked(){return this.state.isStateRanked();}

    public String getStateName(){
        return state.toString();
    }

    public boolean isDelivery(){
        return deliverType.isDelivery();
    }

    public boolean clientHaveToPickUp(){
        return deliverType.haveToPickUp();
    }

    public Credit customerPayment(){ return new Credit(menu.priceWithAmount(menusAmount));}

    public Double orderPrice(){
        //TODO
        //  DUDA DE ENUNCIADO SI GUARDAR EL PRECIO ABONADO
        return menu.priceWithAmount(this.menusAmount);
    }

    public void notifyClientIfTheirPriceHasBeenUpdated() {
        //TODO
        //  LLAMAR AL SERVICE PARA CALCULAR LA CANTIDAD TOTAL DE PEDIDOS QUE FUERON REALIZADOS
        //  Y SI HAY QUE AVISARLE AL CLIENTE DEL NUEVO PRECIO Y DEBITARLE EN CASO DE QUE LO SEA.
    }
}
