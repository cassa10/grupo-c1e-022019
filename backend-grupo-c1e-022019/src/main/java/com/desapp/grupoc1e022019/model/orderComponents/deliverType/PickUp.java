package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import javax.persistence.Entity;

@Entity
public class PickUp extends DeliverType  {

    public boolean haveToPickUp(){
        return true;
    }

    public boolean isDelivery(){
        return false;
    }

    @Override
    public int getDeliverTimeAverageInMinutes(int averageDeliveryTimeInMinutes,Double deliveryMaxDistanceInKM){
        return averageDeliveryTimeInMinutes;
    }
}
