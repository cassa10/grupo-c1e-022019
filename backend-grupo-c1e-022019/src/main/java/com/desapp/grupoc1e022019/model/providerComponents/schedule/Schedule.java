package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Entity
public class Schedule  extends EntityId{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "schedule_bussines_time_mapping",
            joinColumns = {@JoinColumn(name = "id_schedule", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_set_of_bussiness_time", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "id_day_of_week")
    private Map<DayOfWeek, SetOfBussinessTime> daysAndHours;

    public Schedule(){}

    public Schedule(Map<DayOfWeek,SetOfBussinessTime> daysAndHours){
        this.daysAndHours = daysAndHours;
    }

    public Map<DayOfWeek,SetOfBussinessTime> getDaysAndBussinessTime() {
        return daysAndHours;
    }

    public void addBussinessTime(DayOfWeek day,BussinessTime newBussinessHour){
        SetOfBussinessTime value = new SetOfBussinessTime();
        if(daysAndHours.containsKey(day)){
            value = daysAndHours.get(day);
        }
        value.add(newBussinessHour);
        daysAndHours.put(day,value);
    }

    public void deleteBussinessTime(DayOfWeek day){
        daysAndHours.remove(day);
    }

    public void setBussinessTime(DayOfWeek day,SetOfBussinessTime newBussinessHours){
        daysAndHours.put(day,newBussinessHours);
    }

    public Set<BussinessTime> getBussinessHoursOf(DayOfWeek day){
        return daysAndHours.get(day).getBussinessTimes();
    }

    public Set<DayOfWeek> getDays(){
        return daysAndHours.keySet();
    }

    public Collection<SetOfBussinessTime> getBussinessTimes(){
        return daysAndHours.values();
    }

    public boolean containsDayAndBussinessTime(DayOfWeek dayOfWeek,SetOfBussinessTime setOfBussinessTime){
        if(daysAndHours.containsKey(dayOfWeek)){
            return this.daysAndHours.get(dayOfWeek).containsAll(setOfBussinessTime);
        }
        return false;
    }
}
