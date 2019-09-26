package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.exception.ClientCannotBuyOrderException;
import com.desapp.grupoc1e022019.model.Order;

public class CannotBuyClient extends StateClient {

    @Override
    public boolean clientHaveToRank(){
        return true;
    }

    @Override
    public boolean isCannotBuyClient(){
        return true;
    }

    @Override
    public void buyOrder(Order order){
        throw new ClientCannotBuyOrderException("You can´t buy until you rank your delivered orders");
    }
}
