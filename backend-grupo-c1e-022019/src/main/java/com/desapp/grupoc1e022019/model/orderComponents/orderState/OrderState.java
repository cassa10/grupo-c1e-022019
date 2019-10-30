package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public abstract class OrderState extends EntityId {

    @Override
    public abstract String toString();

    public void delivered(Order order){}

    public void cancelled(Order order){}

    public void sending(Order order){}

    public boolean isStateCancelled(){return false;}

    public boolean isStateConfirmed(){return false;}

    public boolean isStateDelivered(){return false;}

    public boolean isStatePending(){return false;}

    public boolean isStateSending(){return false;}

    public boolean isStateRanked(){return false;}

    public void confirm(Order order){
        //Confirm the order
        //Only Pending state will override this
    }

    public void rate(Integer score, Order order){
        throw new RatingForbiddenException("Rating a non-delivered order is forbidden");
    }
}
