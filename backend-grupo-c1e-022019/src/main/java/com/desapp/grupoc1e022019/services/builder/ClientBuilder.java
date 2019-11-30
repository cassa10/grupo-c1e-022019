package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.clientState.NormalClientState;
import com.desapp.grupoc1e022019.model.clientState.ClientState;

public class ClientBuilder {

    private String firstName;
    private String lastName;
    private String googleId;
    private String imageUrl;
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    private Credit credit = new Credit();
    private ClientState clientState = new NormalClientState();

    public static ClientBuilder aClient(){
        return new ClientBuilder();
    }

    public Client build(){
        return new Client(firstName,lastName,email,googleId,imageUrl,phoneNumber,
                location,address,credit, clientState,0);
    }

    public ClientBuilder withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public ClientBuilder withGoogleId(String googleId){
        this.googleId = googleId;
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

    public ClientBuilder withStateClient(ClientState clientState){
        this.clientState = clientState;
        return this;
    }
}
