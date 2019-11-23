package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import java.sql.Time;
import java.util.Iterator;

@Entity
public class BusinessTime extends EntityId {

    private Time open;
    private Time close;

    public BusinessTime(){}

    public BusinessTime(Time open, Time close){
        this.close = close;
        this.open = open;
    }

    public Time getTimeOpen(){
        return open;
    }

    public Time getTimeClose() {
        return close;
    }

    public Time getOpen() {
        return open;
    }

    public void setOpen(Time open) {
        this.open = open;
    }

    public Time getClose() {
        return close;
    }

    public void setClose(Time close) {
        this.close = close;
    }

    @Override
    public boolean equals(Object obj){
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        BusinessTime bt = (BusinessTime) obj;
        return bt.getOpen().equals(getOpen()) && bt.getClose().equals(getClose());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((open == null) ? 0 : open.hashCode());
        result = prime * result + ((close == null) ? 0 : close.hashCode());
        return result;
    }

    public boolean hasIntersection(Iterator<BusinessTime> iterator) {
        boolean value = false;
        BusinessTime bCompare;
        while(iterator.hasNext()){
            bCompare = iterator.next();
            value = value || (! this.isNotIntersection(bCompare));
        }

        return value;
    }

    private boolean isNotIntersection(BusinessTime bCompare) {
        return (close.before(bCompare.open)) || (open.after(bCompare.close));
    }

    public boolean isValidBusinessTime(){
        return open.before(close);
    }
}
