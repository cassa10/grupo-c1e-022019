package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBusinessTime;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public class ScheduleDTO {

    private String googleId;
    private String tokenAccess;
    private Map<DayOfWeek, SetOfBusinessTime> daysWithHours;

    public ScheduleDTO(){}

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public Map<DayOfWeek, SetOfBusinessTime> getDaysWithHours() {
        return daysWithHours;
    }

    public void setDaysWithHours(Map<DayOfWeek, SetOfBusinessTime> daysWithHours) {
        this.daysWithHours = daysWithHours;
    }

    public boolean isValidSchedule() {
        return daysWithHours != null && hasValidBusinessHour() && hasValidSetOfBussinessHourValues();
    }

    private boolean hasValidBusinessHour() {
        boolean value = true;

        if(daysWithHours.values().isEmpty()){
            return false;
        }

        for(SetOfBusinessTime set : daysWithHours.values()){

            value = value && set.allBusinessHourAreValid();
        }

        return value;
    }

    public boolean hasValidSetOfBussinessHourValues(){
        Set<DayOfWeek> days = daysWithHours.keySet();
        boolean isValid = true;

        if(days.isEmpty()){
            return false;
        }

        for(DayOfWeek day : days ){
            isValid = isValid && ! daysWithHours.get(day).hasBussinessIntersection();
        }

        return isValid;
    }

    public Schedule getSchedule() {
        return new Schedule(daysWithHours);
    }
}
