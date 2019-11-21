package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public abstract class DeliverType extends EntityId {

    protected LocalDateTime deliverDate;

    public DeliverType(){}

    protected DeliverType(LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }

    public abstract boolean haveToPickUp();
    public abstract boolean isDelivery();
    public abstract int getDeliverTimeAverageInMinutes(int averageDeliveryTimeInMinutes,Double deliveryMaxDistanceInKM);
    public abstract Address getDestinationOrder(Order order);

    public LocalDateTime getDeliverDate(){
        return this.deliverDate;
    }

    public void setDeliverDate(LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }
}
