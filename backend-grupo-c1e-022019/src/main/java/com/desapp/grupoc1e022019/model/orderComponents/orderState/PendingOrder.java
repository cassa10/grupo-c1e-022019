package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class PendingOrder extends OrderState {

    @Override
    public boolean isStatePending(){return true;}

    @Override
    public void cancelled(Order order){
        order.setState(new CancelledOrder());
        order.getClient().deposit(order.customerPayment());
        //TODO
        // LLAMAR AL SERVICE EN LA PROX ENTREGA
    }

    @Override
    public void update(Order order) {
        //TODO
        // ---DUDA DE ENUNCIADO-----
        // EMAIL SENDER AL CLIENTE CUANDO EL PRECIO DISMINUYE
        order.setState(new ConfirmedOrder());
        order.notifyClientIfTheirPriceHasBeenUpdated();
    }

    @Override
    public String toString(){
        return "PENDING";
    }
}
