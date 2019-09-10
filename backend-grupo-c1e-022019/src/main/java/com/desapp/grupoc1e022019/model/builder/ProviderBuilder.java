package com.desapp.grupoc1e022019.model.builder;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.Schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.Schedule.DaysAndBussinessTime;
import com.desapp.grupoc1e022019.model.Schedule.Schedule;
import com.desapp.grupoc1e022019.model.location.Address;
import com.desapp.grupoc1e022019.model.location.Coord;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProviderBuilder {
    private String name;
    private String logo;
    private String city;
    private Address address;
    private String description;
    private String webURL;
    private String email;
    private String telNumber;
    private Schedule schedule;
    private Integer deliveryMaxDistanceInKM;
    private List<Menu> menus;

    public ProviderBuilder(){
        name = "builderName";
        logo = "builderLogo";
        city = "builderCity";
        address = new Address(new Coord(0d,0d),"Quilmes, Buenos Aires, Argentina");
        description = "builderDescription";
        webURL = "builderWebURL";
        email = "builderEmail";
        telNumber = "builderTelNumber";
        HashSet<DaysAndBussinessTime> daysOfWeekAndBussinessTime = getDayOfWeeksMondayToFriday9To5();
        schedule = new Schedule(daysOfWeekAndBussinessTime);
        deliveryMaxDistanceInKM = 4;
        menus = new ArrayList<>();
    }

    private HashSet<DaysAndBussinessTime> getDayOfWeeksMondayToFriday9To5() {
        HashSet<DaysAndBussinessTime> daysAndTime = new HashSet<>();
        BussinessTime nineToFive = new BussinessTime(Time.valueOf("08:00:00"),Time.valueOf("17:00:00"));
        daysAndTime.add(new DaysAndBussinessTime(DayOfWeek.MONDAY,nineToFive));
        daysAndTime.add(new DaysAndBussinessTime(DayOfWeek.TUESDAY,nineToFive));
        daysAndTime.add(new DaysAndBussinessTime(DayOfWeek.WEDNESDAY,nineToFive));
        daysAndTime.add(new DaysAndBussinessTime(DayOfWeek.THURSDAY,nineToFive));
        daysAndTime.add(new DaysAndBussinessTime(DayOfWeek.FRIDAY,nineToFive));
        return daysAndTime;
    }

    public static ProviderBuilder aProvider() {
        return new ProviderBuilder();
    }

    public Provider build() {
        return new Provider(name,logo,city, address,description,webURL,email,telNumber,schedule,deliveryMaxDistanceInKM,menus);
    }

    public ProviderBuilder withMenus(ArrayList<Menu> menus) {
        this.menus = menus;
        return this;
    }
}
