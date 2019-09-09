package com.desapp.grupoc1e022019.model;

public class Client {

    private String firstName;
    private String lastName;
    //TODO
    // Email have to be ID?
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    private Double credit;

    public Client(String firstName, String lastName, String email, String phoneNumber, String location, String address, Double credit){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLocation(location);
        this.setAddress(address);
        this.setCredit(credit);
    }

    //-----------------------------
    //Getters And Setters --Start--
    //-----------------------------

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    //---------------------------
    //Getters And Setters --END--
    //---------------------------
}
