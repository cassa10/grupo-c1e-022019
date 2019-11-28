package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import javax.persistence.Entity;

@Entity
public class CancelledOrder extends OrderState {

    @Override
    public boolean isStateCancelled(){return true;}

    @Override
    public String toString(){
        return "CancelledOrder";
    }
}
