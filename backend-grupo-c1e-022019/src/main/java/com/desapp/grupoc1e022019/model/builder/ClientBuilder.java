package com.desapp.grupoc1e022019.model.builder;

import com.desapp.grupoc1e022019.model.Client;

public class ClientBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    private Double credit;


    public ClientBuilder aClient(){
        return this;
    }

    public ClientBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public ClientBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public ClientBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public ClientBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ClientBuilder withLocation(String location){
        this.location = location;
        return this;
    }

    public ClientBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public ClientBuilder withCredit(Double credit){
        this.credit = credit;
        return this;
    }

    public Client build(){
        return new Client(firstName,lastName,email,phoneNumber,location,address,credit);
    }


}
