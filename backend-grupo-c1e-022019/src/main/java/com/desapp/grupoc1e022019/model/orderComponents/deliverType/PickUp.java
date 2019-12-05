package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class PickUp extends DeliverType  {

    public PickUp(){}

    public PickUp(LocalDateTime deliverDate){
        super.deliverDate = deliverDate;
    }

    @Override
    public boolean haveToPickUp(){
        return true;
    }

    @Override
    public boolean isDelivery(){
        return false;
    }

    @Override
    public int getDeliverTimeAverageInMinutes(int averageDeliveryTimeInMinutes,Double deliveryMaxDistanceInKM){
        return averageDeliveryTimeInMinutes;
    }

    @Override
    public  Address getDestinationOrder(Order order){
        return order.getMenu().getProvider().getAddress();
    }

    @Override
    public String toString(){
        return "PickUp";
    }

    @Override
    public String customDeliverMessage(Order order){
        return "Your Order deliver is pick up. So, your order will be ready to pick up at '"
                + order.getDeliverDate().plusMinutes(order.getDeliverTimeAverageInMinutes()).toString() + "'. ";
    }
}
