package com.desapp.grupoc1e022019.model;

import java.util.Objects;

public class Credit extends EntityId{
    Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Objects.equals(amount, credit.amount);
    }

    public Credit(Double amount){
        this.amount = amount;
    }
    public Credit sum(Credit credit){
        return new Credit(amount + credit.getAmount());
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isGreaterOrEqual(Credit credits) {
        return this.amount >= credits.amount;
    }

    public Credit minus(Credit credits) {
        return new Credit(this.amount - credits.amount);
    }
}
