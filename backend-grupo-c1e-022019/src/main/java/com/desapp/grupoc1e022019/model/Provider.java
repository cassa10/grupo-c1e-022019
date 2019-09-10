package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.exception.RepeatedIDException;
import com.desapp.grupoc1e022019.model.Schedule.Schedule;
import com.desapp.grupoc1e022019.model.location.Address;

import java.util.List;
import java.util.stream.Collectors;

public class Provider {
    private List<Menu> menus;
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

    public Provider(String name, String logo, String city, Address address, String description, String webURL, String email, String telNumber, Schedule schedule, Integer deliveryMaxDistanceInKM, List<Menu> menus) {

        this.name = name;
        this.logo = logo;
        this.city = city;
        this.address = address;
        this.description = description;
        this.webURL = webURL;
        this.email = email;
        this.telNumber = telNumber;
        this.schedule = schedule;
        this.deliveryMaxDistanceInKM = deliveryMaxDistanceInKM;
        this.menus = menus;

    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void addMenu(Menu menu) {
        checkMaxMenus();
        checkIdNotRepeated(menu.id());
        menus.add(menu);
    }

    private void checkIdNotRepeated(Integer id ) {
        if( menus.stream().anyMatch(menu -> menu.id().equals(id))){
            throw new RepeatedIDException("The menu's id is already in our system :)");
        }
    }

    private void checkMaxMenus(){
        if(this.menus.size() == 20){
            throw new MaximumMenusSizeException("Can't add more than twenty menus");
        }
    }

    public void deleteMenu(Menu menu) {
        menus.remove(menu);
    }

    //TODO
    // Hacerlo por Base de dato
    public void updateMenu(int id, Menu updatedMenu) {
        menus = menus.stream().map((m) -> swap(m,updatedMenu)).collect(Collectors.toList());
    }

    private Menu swap(Menu menu, Menu updatedMenu) {
        if(menu.id().equals(updatedMenu.id())){
            return updatedMenu;
        }
        return menu;
    }
}
