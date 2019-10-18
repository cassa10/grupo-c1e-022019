package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import java.sql.Time;

@Entity
public class BussinessTime extends EntityId {
    private Time from;
    private Time to;

    public BussinessTime(){}

    public BussinessTime(Time from, Time to){
        this.to = to;
        this.from = from;
    }

    public Time getTimeFrom(){
        return from;
    }

    public Time getTimeTo() {
        return to;
    }
}
