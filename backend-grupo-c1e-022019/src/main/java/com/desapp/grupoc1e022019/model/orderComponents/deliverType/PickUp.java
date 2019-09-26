package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

public class PickUp implements DeliverType  {

    //TODO
    // PREGUNTAR EL CALCULO DE DELIVERTIME

    public boolean haveToPickUp(){
        return true;
    }

    public boolean isDelivery(){
        return false;
    }
}
