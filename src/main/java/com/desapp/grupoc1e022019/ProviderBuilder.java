package com.desapp.grupoc1e022019;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
/**
 * PREGUNTAR:
 *      Los builders se testean??*/
public class ProviderBuilder {
    private String name;
    private String logo;
    private String city;
    private MapsLocation mapsLocation;
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
        mapsLocation = new MapsLocation(0d,0d);
        description = "builderDescription";
        webURL = "builderWebURL";
        email = "builderEmail";
        telNumber = "builderTelNumber";
        ArrayList<DayOfWeek> daysOfWeek = getDayOfWeeksMondayToFriday();
        schedule = new Schedule(daysOfWeek);
        deliveryMaxDistanceInKM = 4;
        menus = new ArrayList<>();
    }

    private ArrayList<DayOfWeek> getDayOfWeeksMondayToFriday() {
        ArrayList<DayOfWeek> daysOfWeek = new ArrayList<>();
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
        return new Provider(name,logo,city,mapsLocation,description,webURL,email,telNumber,schedule,deliveryMaxDistanceInKM,menus);
    }

    public ProviderBuilder withMenus(ArrayList<Menu> menus) {
        this.menus = menus;
        return this;
    }
}
