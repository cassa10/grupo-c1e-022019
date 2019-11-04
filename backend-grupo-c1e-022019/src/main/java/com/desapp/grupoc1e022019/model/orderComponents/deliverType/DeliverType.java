package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public abstract class DeliverType extends EntityId {

    public abstract boolean haveToPickUp();
    public abstract boolean isDelivery();
    public abstract int getDeliverTimeAverageInMinutes(int averageDeliveryTimeInMinutes,Double deliveryMaxDistanceInKM);
}
