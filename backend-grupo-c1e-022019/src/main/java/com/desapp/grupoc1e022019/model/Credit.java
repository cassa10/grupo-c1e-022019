package com.desapp.grupoc1e022019.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Credit extends EntityId{

    private Double amount;

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

    public Credit(){
        this.amount = 0d;
    }

    public Credit sum(Credit credit){
        return new Credit(amount + credit.getAmount());
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isGreaterOrEqual(Credit credits) {
        return this.amount >= credits.amount;
    }

    public Credit minus(Credit credits) {
        return new Credit(this.amount - credits.amount);
    }
}
