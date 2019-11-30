package com.desapp.grupoc1e022019.model.clientState;

import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public abstract class ClientState extends EntityId {

    @Override
    public abstract String toString();

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
