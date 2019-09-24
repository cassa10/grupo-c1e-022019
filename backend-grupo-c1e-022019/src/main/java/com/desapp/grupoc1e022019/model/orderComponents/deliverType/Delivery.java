package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;

import java.time.LocalDateTime;

public class Delivery implements DeliverType {

    //TODO
    // PREGUNTAR EL CALCULO DE DELIVERTIME

    public boolean haveToPickUp(){
        return false;
    }

    public boolean isDelivery(){
        return true;
    }

    public LocalDateTime minOrderDeliverTime(Client client, Menu menu){
        return null;
    }

    public LocalDateTime maxOrderDeliverTime(Client client,Menu menu){
        return null;
    }
}
