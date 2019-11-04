package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.clientState.CannotBuyClient;
import com.desapp.grupoc1e022019.model.clientState.NormalClient;
import com.desapp.grupoc1e022019.model.clientState.StateClient;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client extends EntityId{

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String location;
    private String address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_CREDIT")
    private Credit credit;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_CLIENT_STATE")
    private StateClient stateClient;
    @JsonManagedReference
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "client"
    )
    private List<Order> ordersHaveToRank;

    public Client(){}

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

    public StateClient getStateClient() {
        return stateClient;
    }

    public List<Order> getOrdersHaveToRank() {
        return ordersHaveToRank;
    }

    public void setOrdersHaveToRank(List<Order> ordersHaveToRank) {
        this.ordersHaveToRank = ordersHaveToRank;
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
            throw new InsufficientCreditException("Client doesn't have enough credits");
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

    public void buyAnOrder(Order order){
        this.stateClient.buyOrder(order);
    }

    public void cancellOrder(Order order){ order.cancelled();}

    public boolean isNormalClient(){
        return stateClient.isNormal();
    }

    public boolean isClientHaveToRank(){
        return stateClient.clientHaveToRank();
    }

    public boolean isCannotBuyClient(){
        return stateClient.isCannotBuyClient();
    }

    public int getSizeOrderHaveToRank(){ return ordersHaveToRank.size();}
}
