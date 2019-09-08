package com.desapp.grupoc1e022019.model.builder;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.location.Address;
import com.desapp.grupoc1e022019.model.location.Coord;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * PREGUNTAR:
 *      Los builders se testean??*/
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
    private ArrayList<Menu> menus;

    public ProviderBuilder(){
        name = "builderName";
        logo = "builderLogo";
        city = "builderCity";
        address = new Address(new Coord(0d,0d),"Quilmes, Buenos Aires, Argentina");
        description = "builderDescription";
        webURL = "builderWebURL";
        email = "builderEmail";
        telNumber = "builderTelNumber";
        HashSet<DayOfWeek> daysOfWeek = getDayOfWeeksMondayToFriday();
        schedule = new Schedule(daysOfWeek);
        deliveryMaxDistanceInKM = 4;
        menus = new ArrayList<>();
    }

    private HashSet<DayOfWeek> getDayOfWeeksMondayToFriday() {
        HashSet<DayOfWeek> daysOfWeek = new HashSet<>();
        daysOfWeek.add(DayOfWeek.MONDAY);
        daysOfWeek.add(DayOfWeek.TUESDAY);
        daysOfWeek.add(DayOfWeek.WEDNESDAY);
        daysOfWeek.add(DayOfWeek.THURSDAY);
        daysOfWeek.add(DayOfWeek.FRIDAY);
        return daysOfWeek;
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
