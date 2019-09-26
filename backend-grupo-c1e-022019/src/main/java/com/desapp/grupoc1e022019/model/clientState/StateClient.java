package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.model.Order;

public abstract class StateClient {

    public abstract void buyOrder(Order order);

    public boolean isNormal(){
        return false;
    }

    public boolean isCannotBuyClient(){
        return false;
    }

    public boolean clientHaveToRank(){
        return false;
    }
}
