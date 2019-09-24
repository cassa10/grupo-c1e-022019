package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;

import java.time.LocalDateTime;

public interface DeliverType {

    LocalDateTime minOrderDeliverTime(Client client, Menu menu);
    LocalDateTime maxOrderDeliverTime(Client client,Menu menu);
    boolean haveToPickUp();
    boolean isDelivery();

}
