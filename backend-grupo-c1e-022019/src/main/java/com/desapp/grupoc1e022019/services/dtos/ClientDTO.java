package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.clientState.StateClient;

public class ClientDTO {

    private long id;
    private GoogleAuthDTO googleAuthDTO;
    private String firstName;
    private String lastName;
    private String email;
    private String googleId;
    private String imageUrl;
    private String phoneNumber;
    private String location;
    private String address;
    private Credit credit;
    private StateClient stateClient;

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        setFirstName(client.getFirstName());
        setLastName(client.getLastName());
        setEmail(client.getEmail());
        setPhoneNumber(client.getPhoneNumber());
        setLocation(client.getLocation());
        setAddress(client.getAddress());
        setCredit(client.getCredit());
        setStateClient(client.getStateClient());
        setGoogleId(client.getGoogleId());
        setImageUrl(client.getImageUrl());
    }

    public GoogleAuthDTO getGoogleAuthDTO() {
        return googleAuthDTO;
    }

    public void setGoogleAuthDTO(GoogleAuthDTO googleAuthDTO) {
        this.googleAuthDTO = googleAuthDTO;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

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

    public StateClient getStateClient() {
        return stateClient;
    }

    public void setStateClient(StateClient stateClient) {
        this.stateClient = stateClient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
