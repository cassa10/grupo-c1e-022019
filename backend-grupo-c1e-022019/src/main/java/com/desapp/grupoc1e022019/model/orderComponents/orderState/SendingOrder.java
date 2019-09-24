package com.desapp.grupoc1e022019.model.orderComponents.orderState;


import com.desapp.grupoc1e022019.model.Order;

public class SendingOrder extends OrderState {

    @Override
    public boolean isStateSending(){return true;}

    @Override
    public void delivered(Order order){
        order.setState(new DeliveredOrder());
        order.getClient().haveToRankOrder(order);

        //TODO
        //  LLAMAR AL SERVICE NECESARIO EN LA ENTREGA 2
    }

    @Override
    public String toString(){
        return "SENDING";
    }
}
