package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.providerComponents.location.Address;

public class ProviderDTO  {

    public ProviderDTO(){}

    private Long id;
    private String name;
    private String logo;
    private String city;
    /**
     * En mi opinion, el provider deberia inicar sin menues*/
    //private List<MenuDTO> menus ;

    private Address address;
    private String description;
    private String webURL;
    private String email;
    private String telNumber;
    private Double deliveryMaxDistanceInKM;

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
}
