package com.desapp.grupoc1e022019;

import java.util.ArrayList;

public class Provider {
    private final ArrayList<Menu> menus;
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

    public Provider(String name, String logo, String city, MapsLocation mapsLocation, String description, String webURL, String email, String telNumber, Schedule schedule, Integer deliveryMaxDistanceInKM, ArrayList<Menu> menus) {

        this.name = name;
        this.logo = logo;
        this.city = city;
        this.mapsLocation = mapsLocation;
        this.description = description;
        this.webURL = webURL;
        this.email = email;
        this.telNumber = telNumber;
        this.schedule = schedule;
        this.deliveryMaxDistanceInKM = deliveryMaxDistanceInKM;
        this.menus = menus;

    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void addMenu(Menu menu) {
        checkMaxMenus();
        menus.add(menu);
    }

    private void checkMaxMenus(){
        if(this.menus.size() >= 20){
            throw new MaximumMenusSizeException("Can't add more than twenty menus");
        }
    }

    public void deleteMenu(Menu menu) {
        menus.remove(menu);
    }


}
