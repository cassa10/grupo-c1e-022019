package com.desapp.grupoc1e022019.model.builder;

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
    private Credit credit;
    private StateClient stateClient = new NormalClient();
    private List<Order> orderHaveToRank = new ArrayList<>();


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

    public ClientBuilder withCredit(Credit credit){
        this.credit = credit;
        return this;
    }

    public ClientBuilder withStateClient(StateClient stateClient){
        this.stateClient = stateClient;
        return this;
    }

    public ClientBuilder withORdersHaveToRank(List<Order> orderHaveToRank){
        this.orderHaveToRank = orderHaveToRank;
        return this;
    }

    public Client build(){
        return new Client(firstName,lastName,email,phoneNumber,
                location,address,credit,stateClient,orderHaveToRank);
    }


}
