package com.desapp.grupoc1e022019.model.orderState;

import com.desapp.grupoc1e022019.model.Order;

public class CancelledOrder extends OrderState {

    @Override
    public boolean isStateCancelled(){return true;}
}
