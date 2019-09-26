package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;

public class NormalClient extends StateClient {

    @Override
    public boolean isNormal(){
        return true;
    }

    public void buyOrder(Order order){
        //TODO
        // UTILIZAR SERVICE
        order.getClient().debit(new Credit(order.orderPrice()));
    }
}
