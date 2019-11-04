package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.clientState.NormalClient;
import com.desapp.grupoc1e022019.model.clientState.StateClient;

import java.util.ArrayList;
import java.util.List;

public class ClientBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    private Credit credit = new Credit();
    private StateClient stateClient = new NormalClient();

    public static ClientBuilder aClient(){
        return new ClientBuilder();
    }

    public Client build(){
        List<Order> orderHaveToRank = new ArrayList<>();
        return new Client(firstName,lastName,email,phoneNumber,
                location,address,credit,stateClient,orderHaveToRank);
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

    public ClientBuilder withCredit(Credit credit){
        this.credit = credit;
        return this;
    }

    public ClientBuilder withStateClient(StateClient stateClient){
        this.stateClient = stateClient;
        return this;
    }
}
