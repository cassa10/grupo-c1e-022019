package com.desapp.grupoc1e022019.model;

import java.time.DayOfWeek;
import java.util.Set;

public class Schedule {
    private Set<DayOfWeek> days;

    public Schedule(Set<DayOfWeek> days){
        this.days = days;
    }
}
