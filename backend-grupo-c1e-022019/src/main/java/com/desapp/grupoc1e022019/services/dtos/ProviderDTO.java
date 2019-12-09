package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;

public class ProviderDTO  {

    private Long id;
    private String googleId;
    private String tokenAccess;
    private String name;
    private String logo;
    private String city;
    private Address address;
    private String description;
    private String webURL;
    private String email;
    private String telNumber;
    private Double deliveryMaxDistanceInKM;
    private Schedule schedule;

    public ProviderDTO(){}

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getDeliveryMaxDistanceInKM() {
        return deliveryMaxDistanceInKM;
    }

    public void setDeliveryMaxDistanceInKM(Double deliveryMaxDistanceInKM) {
        this.deliveryMaxDistanceInKM = deliveryMaxDistanceInKM;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    @Override
    public String toString(){
        return "{ "+ "id: "+ id + " ,googleId: " + googleId + " ,tokenAccess: " + tokenAccess
                + " ,name: "+name +" ,logo: " +logo  + " ,city: "+ city + " ,address: " + address
                + " ,description: "+ description + " ,webURL: " + webURL + " ,email: "+ email +
                " ,telNumber: "+ telNumber+ " ,deliveryMaxDistanceInKM: "+ deliveryMaxDistanceInKM + " ,schedule: "+schedule +" }";
    }

    public boolean formPostProviderIsOk() {

        return (isValidString(name) && isValidString(logo) && isValidString(city) &&
                    isValidDescription() && isValidString(webURL) && isValidString(email) &&
                    isValidAddress() && isValidTelNumber() && isValidDeliveryMaxDistanceInKM());
    }

    private boolean isValidDeliveryMaxDistanceInKM() {
        // 0.5 <= deliveryMaxDistanceInKM <= 60.499..
        float roundDistance = Math.round(deliveryMaxDistanceInKM);
        return roundDistance >= 1 && roundDistance < 60;
    }

    private boolean isValidTelNumber() {
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";

        return telNumber.trim().matches(regex);
    }

    private boolean isValidAddress() {
        return address != null && address.isValid();
    }

    private boolean isValidDescription() {
        //ENUNCIADO PIDE QUE LA DESCRIPCION SEA 30 <= X <= 200
        return isValidString(description) && description.trim().length() >= 30 && description.trim().length() <= 200;
    }

    private boolean isValidString(String string) {
        return string != null && string.trim().length() > 0;
    }


    public void trimAllStringInputs() {
        name = name.trim();
        logo = logo.trim();
        city = city.trim();
        description = description.trim();
        email = email.trim();
        webURL = webURL.trim();
        telNumber = telNumber.trim();
    }

    public void trimAllStringBasicInfo() {
        name = name.trim();
        logo = logo.trim();
        city = city.trim();
        description = description.trim();
        webURL = webURL.trim();
        telNumber = telNumber.trim();
    }

    public boolean isValidBasicInfo() {
        return (isValidString(name) && isValidString(logo) &&
                isValidDescription() && isValidString(webURL) &&
                isValidTelNumber() && isValidDeliveryMaxDistanceInKM());
    }
}
