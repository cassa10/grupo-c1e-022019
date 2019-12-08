package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class CannotBuyClientState extends ClientState {

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
    }

    @Override
    public String toString(){
        return "CannotBuyClient";
    }
}
