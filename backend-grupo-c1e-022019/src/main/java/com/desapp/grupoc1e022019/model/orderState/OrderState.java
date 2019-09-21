package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.Order;

public abstract class OrderState {
    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass());
    }

    public void update(Order order){
        //Only Pending state will override this
    }

    public void rate(Integer score, Order order){
        throw new RatingForbiddenException("Rating a non-delivered order is forbidden");
    }
}
