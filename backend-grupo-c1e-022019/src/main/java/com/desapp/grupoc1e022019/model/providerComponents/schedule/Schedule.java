package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import java.time.DayOfWeek;
import java.util.Map;

public class Schedule {
    private Map<DayOfWeek,BussinessTime> daysAndHours;

    public Schedule(Map<DayOfWeek,BussinessTime> daysAndHours){
        this.daysAndHours = daysAndHours;
    }

    public Map<DayOfWeek,BussinessTime> getDaysAndBussinessTime() {
        return daysAndHours;
    }
}
