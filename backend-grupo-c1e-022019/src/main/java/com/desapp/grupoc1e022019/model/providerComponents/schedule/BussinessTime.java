package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import java.sql.Time;

public class BussinessTime {
    private Time from;
    private Time to;

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
