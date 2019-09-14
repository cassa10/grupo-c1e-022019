package com.desapp.grupoc1e022019.model.OrderState;

public abstract class OrderState {
    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(this.getClass());
    }
}
