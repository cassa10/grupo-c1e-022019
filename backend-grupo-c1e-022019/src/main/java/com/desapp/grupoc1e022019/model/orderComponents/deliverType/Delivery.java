package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import javax.persistence.Entity;

@Entity
public class Delivery extends DeliverType {

    //TODO
    // PREGUNTAR EL CALCULO DE DELIVERTIME

    public boolean haveToPickUp(){
        return false;
    }

    public boolean isDelivery(){
        return true;
    }
}
