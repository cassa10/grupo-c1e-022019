package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import javax.persistence.Entity;

@Entity
public class PickUp extends DeliverType  {

    //TODO
    // PREGUNTAR EL CALCULO DE DELIVERTIME

    public boolean haveToPickUp(){
        return true;
    }

    public boolean isDelivery(){
        return false;
    }
}
