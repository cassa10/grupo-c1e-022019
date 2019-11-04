package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import javax.persistence.Entity;

@Entity
public class Delivery extends DeliverType {

    @Override
    public boolean haveToPickUp(){
        return false;
    }

    @Override
    public boolean isDelivery(){
        return true;
    }

    @Override
    public int getDeliverTimeAverageInMinutes(int averageDeliveryTimeInMinutes,Double deliveryMaxDistanceInKM){
        return averageDeliveryTimeInMinutes + deliveryMaxDistanceInKM.intValue() * 8 ;
    }
}
