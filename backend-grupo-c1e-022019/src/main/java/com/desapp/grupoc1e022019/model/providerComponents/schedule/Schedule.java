package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Schedule  extends EntityId{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "schedule_mapping",
            joinColumns = {@JoinColumn(name = "id_schedule", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_set_of_bussiness_time", referencedColumnName = "id")})
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, SetOfBusinessTime> daysAndHours;

    public Schedule(){
        daysAndHours = new HashMap<>();
    }

    public Schedule(Map<DayOfWeek, SetOfBusinessTime> daysAndHours){
        this.daysAndHours = daysAndHours;
    }

    public Map<DayOfWeek, SetOfBusinessTime> getDaysAndBussinessTime() {
        return daysAndHours;
    }

    public void addBussinessTime(DayOfWeek day, BusinessTime newBussinessHour){
        SetOfBusinessTime value = new SetOfBusinessTime();
        if(daysAndHours.containsKey(day)){
            value = daysAndHours.get(day);
        }
        value.add(newBussinessHour);
        daysAndHours.put(day,value);
    }

    public void deleteBussinessTime(DayOfWeek day){
        daysAndHours.remove(day);
    }

    public void setBussinessTime(DayOfWeek day, SetOfBusinessTime newBussinessHours){
        daysAndHours.put(day,newBussinessHours);
    }

    public Set<BusinessTime> getBussinessHoursOf(DayOfWeek day){
        return daysAndHours.get(day).getBusinessTimes();
    }

    public Set<DayOfWeek> getDays(){
        return daysAndHours.keySet();
    }

    public Collection<SetOfBusinessTime> getBussinessTimes(){
        return daysAndHours.values();
    }

    public boolean containsDayAndBussinessTime(DayOfWeek dayOfWeek, SetOfBusinessTime setOfBusinessTime){
        if(daysAndHours.containsKey(dayOfWeek)){
            return this.daysAndHours.get(dayOfWeek).containsAll(setOfBusinessTime);
        }
        return false;
    }

    public Map<DayOfWeek, SetOfBusinessTime> getDaysAndHours() {
        return daysAndHours;
    }

    public void setDaysAndHours(Map<DayOfWeek, SetOfBusinessTime> daysAndHours) {
        this.daysAndHours = daysAndHours;
    }

    public boolean isValid() {
        return daysAndHours != null && ! daysAndHours.isEmpty();
    }
}
