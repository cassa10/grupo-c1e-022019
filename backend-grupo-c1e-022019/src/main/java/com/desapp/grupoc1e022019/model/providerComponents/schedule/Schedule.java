package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Schedule  {

    private Map<DayOfWeek, Set<BussinessTime>> daysAndHours;

    public Schedule(){}

    public Schedule(Map<DayOfWeek,Set<BussinessTime>> daysAndHours){
        this.daysAndHours = daysAndHours;
    }

    public Map<DayOfWeek,Set<BussinessTime>> getDaysAndBussinessTime() {
        return daysAndHours;
    }

    public void addBussinessTime(DayOfWeek day,BussinessTime newBussinessHour){
        Set<BussinessTime> value = new HashSet<>();
        if(daysAndHours.containsKey(day)){
            value = daysAndHours.get(day);
        }
        value.add(newBussinessHour);
        daysAndHours.put(day,value);
    }

    public void deleteBussinessTime(DayOfWeek day){
        daysAndHours.remove(day);
    }

    public void setBussinessTime(DayOfWeek day,Set<BussinessTime> newBussinessHours){
        daysAndHours.put(day,newBussinessHours);
    }

    public Set<BussinessTime> getBussinessHoursOf(DayOfWeek day){
        return daysAndHours.get(day);
    }

    public Set<DayOfWeek> getDays(){
        return daysAndHours.keySet();
    }

    public Collection<Set<BussinessTime>> getBussinessTimes(){
        return daysAndHours.values();
    }
}
