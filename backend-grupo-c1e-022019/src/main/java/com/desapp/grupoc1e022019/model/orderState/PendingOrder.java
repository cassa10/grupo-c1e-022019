package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class PendingOrder extends OrderState {

    @Override
    public boolean isStatePending(){return true;}

    @Override
    public void cancelled(Order order){
        order.setState(new CancelledOrder());
        //TODO
        // LLAMAR AL SERVICE EN LA PROX ENTREGA
    }

    @Override
    public void update(Order order) {
        order.setState(new ConfirmedOrder());
    }
}
