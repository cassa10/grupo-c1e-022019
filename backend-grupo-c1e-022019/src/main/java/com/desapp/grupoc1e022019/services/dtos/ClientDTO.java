package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.clientState.ClientState;

public class ClientDTO {

    private long id;
    private String googleId;
    private String tokenAccess;
    private GoogleAuthDTO googleAuthDTO;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private String phoneNumber;
    private String location;
    private String address;
    private Credit credit;
    private ClientState clientState;

    public ClientDTO() {
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

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    @Override
    public String toString(){
        return "{ "+ "id: "+ this.id + " ,googleAuthDTO: " +this.googleAuthDTO + " ,firstName: " +this.firstName
                + " ,lastName: "+this.lastName +" ,email: " +this.email  + " ,googleId: "+ this.googleId +
                " ,tokenAccess: "+ tokenAccess + " ,imageUrl: " + this.imageUrl
                + " ,phoneNumber: "+ this.phoneNumber + " ,location: " +this.location+ " ,address: "+ this.address +
                " ,credit: "+ this.credit + " ,StateClient:"+ this.clientState +" }";
    }
}
