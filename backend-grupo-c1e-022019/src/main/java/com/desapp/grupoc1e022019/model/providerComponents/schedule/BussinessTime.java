package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import java.sql.Time;

@Entity
public class BussinessTime extends EntityId {

    private Time open;
    private Time close;

    public BussinessTime(){}

    public BussinessTime(Time open, Time close){
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
        BussinessTime bt = (BussinessTime) obj;
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
}
