package com.desapp.grupoc1e022019.model.orderComponents.orderState;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.Order;

import javax.persistence.Entity;

@Entity
public class RankedOrder extends OrderState {

    @Override
    public boolean isStateRanked(){return true;}

    @Override
    public void rate(Integer score, Order order){
        throw new RatingForbiddenException("This order has been ranked");
    }

    @Override
    public String toString(){
        return "RankedOrder";
    }
}
