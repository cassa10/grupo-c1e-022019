package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.clientState.CannotBuyClientState;
import com.desapp.grupoc1e022019.model.clientState.NormalClientState;
import com.desapp.grupoc1e022019.model.clientState.ClientState;

import javax.persistence.*;

@Entity
public class Client extends EntityId{

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true)
    private String googleId;
    private String imageUrl;
    private String phoneNumber;
    private String location;
    private String address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_credit")
    private Credit credit;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_client_state")
    private ClientState clientState;
    private Integer sizeOrdersHaveToRank;

    public Client(){}

    public Client(String firstName, String lastName, String email, String googleId, String imageUrl, String phoneNumber,
                  String location, String address, Credit credit, ClientState clientState, Integer sizeOrdersHaveToRank){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLocation(location);
        this.setAddress(address);
        this.setCredit(credit);
        this.setClientState(clientState);
        setSizeOrdersHaveToRank(sizeOrdersHaveToRank);
        this.setGoogleId(googleId);
        setImageUrl(imageUrl);
    }

    //-----------------------------
    //Getters And Setters --START--
    //-----------------------------


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

    public ClientState getClientState() {
        return clientState;
    }

    public Integer getSizeOrdersHaveToRank() {
        return sizeOrdersHaveToRank;
    }

    public void setSizeOrdersHaveToRank(Integer sizeOrdersHaveToRank) {
        this.sizeOrdersHaveToRank = sizeOrdersHaveToRank;
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

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
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
            throw new InsufficientCreditException("Client doesn't have enough credits");
        }
    }

    public void haveToRankOrder(){
        sizeOrdersHaveToRank = sizeOrdersHaveToRank + 1;
        this.clientState = new CannotBuyClientState();
    }

    public void orderRanked() {
        sizeOrdersHaveToRank = sizeOrdersHaveToRank - 1;
        if(this.sizeOrdersHaveToRank == 0){
            this.setClientState(new NormalClientState());
        }
    }

    public void buyAnOrder(Order order){
        this.clientState.buyOrder(order);
    }

    public void cancellOrder(Order order){ order.cancelled();}

    public boolean isNormalClient(){
        return clientState.isNormal();
    }

    public boolean isClientHaveToRank(){
        return clientState.clientHaveToRank();
    }

    public boolean isCannotBuyClient(){
        return clientState.isCannotBuyClient();
    }

    public boolean isTypeClient() {return true;}

    public boolean isTypeProvider() {return false;}
}
