package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.exception.ClientCannotBuyOrderException;
import com.desapp.grupoc1e022019.model.Order;

public class CannotBuyClient extends StateClient {

    public void buyOrder(Order order){
        throw new ClientCannotBuyOrderException("You can´t buy until you rank your delivered orders");
    }
}
