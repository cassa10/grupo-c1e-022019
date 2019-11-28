package com.desapp.grupoc1e022019.model.orderComponents.deliverType;

import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Delivery extends DeliverType {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_ADDRESS")
    private Address destination;

    public Delivery() {

    }

    public Delivery(LocalDateTime deliverDate, Address destination) {
        super.deliverDate = deliverDate;
        this.destination = destination;
    }

    @Override
    public  Address getDestinationOrder(Order order){
        return destination;
    }

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

    @Override
    public String toString(){
        return "Delivery";
    }
}
