package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.clientState.CannotBuyClient;
import com.desapp.grupoc1e022019.model.clientState.NormalClient;
import com.desapp.grupoc1e022019.model.clientState.StateClient;

import java.util.List;

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
    private StateClient stateClient;
    private List<Order> ordersHaveToRank;

    public Client(String firstName, String lastName, String email, String phoneNumber,
                  String location, String address, Credit credit,StateClient stateClient,List<Order> ordersHaveToRank){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLocation(location);
        this.setAddress(address);
        this.setCredit(credit);
        this.setStateClient(stateClient);
        this.ordersHaveToRank = ordersHaveToRank;
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

    public StateClient getStateClient() {return this.stateClient;}

    public void setStateClient(StateClient stateClient) {
        this.stateClient = stateClient;
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

    public void haveToRankOrder(Order order){
        this.ordersHaveToRank.add(order);
        this.stateClient = new CannotBuyClient();
    }

    public void orderRanked(Order order) {
        //TODO
        // USAR EL SERVICE
        this.ordersHaveToRank.remove(order);
        if(this.ordersHaveToRank.isEmpty()){
            this.setStateClient(new NormalClient());
        }
    }

    public void buyAOrder(Order order){
        this.stateClient.buyOrder(order);
    }
}
