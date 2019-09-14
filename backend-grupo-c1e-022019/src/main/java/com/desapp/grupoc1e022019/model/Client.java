package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;

public class Client {

    private String firstName;
    private String lastName;
    //TODO
    // Email have to be ID?
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    private Credit credit;
    //TODO
    //  private StateClient; (Si el cliente tiene una puntuacion pendiente no puede comprar)

    public Client(String firstName, String lastName, String email, String phoneNumber, String location, String address, Credit credit){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLocation(location);
        this.setAddress(address);
        this.setCredit(credit);
    }

    //-----------------------------
    //Getters And Setters --START--
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

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    //---------------------------
    //Getters And Setters --END--
    //---------------------------

    public void deposit(Credit anAmountOfMoney) {
        credit = credit.sum(anAmountOfMoney);
    }

    public void debit(Credit credits) {
        if(credit.isGreaterOrEqual(credits)){
            this.credit = credit.minus(credits);
        }
        else{
            throw new InsufficientCreditException("Hey, this account doesn't have enough credits");
        }
    }
}
