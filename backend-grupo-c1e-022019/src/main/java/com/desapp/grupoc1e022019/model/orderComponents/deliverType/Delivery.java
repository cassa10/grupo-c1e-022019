package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

public class Delivery implements DeliverType {

    //TODO
    // PREGUNTAR EL CALCULO DE DELIVERTIME

    public boolean haveToPickUp(){
        return false;
    }

    public boolean isDelivery(){
        return true;
    }
}
