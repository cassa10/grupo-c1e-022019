package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.NormalProvider;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.ProviderState;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.DaysAndBussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;

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
    private Double deliveryMaxDistanceInKM;
    private List<Menu> menus;
    private Credit credit;
    private Integer strikesMenu;
    private ProviderState providerState;

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
        deliveryMaxDistanceInKM = 4d;
        menus = new ArrayList<>();
        credit = new Credit(0d);
        strikesMenu = 0;
        providerState = new NormalProvider();
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
        return new Provider(name,logo,city, address,description,webURL,email,
                telNumber,schedule,credit,deliveryMaxDistanceInKM,menus,providerState, strikesMenu);
    }

    public ProviderBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProviderBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public ProviderBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public ProviderBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public ProviderBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProviderBuilder withWebUrl(String webURL) {
        this.webURL = webURL;
        return this;
    }

    public ProviderBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ProviderBuilder withTelNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public ProviderBuilder withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public ProviderBuilder withDeliveryMaxDistanceInKM(Double deliveryMaxDistanceInKM) {
        this.deliveryMaxDistanceInKM = deliveryMaxDistanceInKM;
        return this;
    }

    public ProviderBuilder withMenus(List<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public ProviderBuilder withCredit(Credit credit){
        this.credit = credit;
        return this;
    }

    public ProviderBuilder withStrikesMenu(Integer strikesMenu) {
        this.strikesMenu = strikesMenu;
        return this;
    }

    public ProviderBuilder withProviderState(ProviderState providerState) {
        this.providerState = providerState;
        return this;
    }


}
