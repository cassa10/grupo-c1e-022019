package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import java.sql.Time;

public class BussinessTime {
    Time from;
    Time to;

    public BussinessTime(Time from, Time to){
        this.to = to;
        this.from = from;
    }
}
