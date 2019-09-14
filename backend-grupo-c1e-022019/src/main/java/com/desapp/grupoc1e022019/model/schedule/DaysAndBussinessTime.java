package com.desapp.grupoc1e022019.model.schedule;

import java.time.DayOfWeek;

public class DaysAndBussinessTime {

    private DayOfWeek day;
    private BussinessTime hours;

    public DaysAndBussinessTime(DayOfWeek day, BussinessTime hours){
        this.day = day;
        this.hours = hours;
    }
}
