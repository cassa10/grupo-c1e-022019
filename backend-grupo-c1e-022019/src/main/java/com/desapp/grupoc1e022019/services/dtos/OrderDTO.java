package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.orderComponents.MenuInfo;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.Delivery;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.PickUp;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.OrderState;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;

import java.time.LocalDateTime;

public class OrderDTO {

    private String tokenAccess;
    private String googleId;
    private long id;
    private long idClient;
    private long idMenu;
    private Integer stars;
    private Integer menusAmount;
    private String deliverType;
    private LocalDateTime deliverDate;
    private Address destination;
    private OrderState orderState;
    private MenuInfo menuInfo;


    public OrderDTO() {}

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getMenusAmount() {
        return menusAmount;
    }

    public void setMenusAmount(Integer menusAmount) {
        this.menusAmount = menusAmount;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public LocalDateTime getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public boolean isDeliveryType(){
        return this.deliverType.equals("delivery");
    }

    public boolean isPickUpType(){
        return this.deliverType.equals("pickup");
    }

    public boolean hasValidDeliverType() {
        return isPickUpType() || isDeliveryType();
    }

    public DeliverType parseDeliveryType() {
        DeliverType value;
        if(isPickUpType()){
            value = new PickUp(this.deliverDate);
        }else{
            value = new Delivery(this.deliverDate,this.destination);
        }
        return value;
    }

    @Override
    public String toString(){
        return "{ "+ "id: "+ id + " ,googleId: " + googleId + " ,tokenAccess: " + tokenAccess
                + " ,idClient: "+idClient +" ,idMenu: " +idMenu  + " ,stars: "+ stars + " ,menusAmount: " + menusAmount
                + " ,deliverType: "+ deliverType + " ,deliverDate: " + deliverDate + " ,destination: "+ destination +
                " ,orderState: "+ orderState +" }";
    }

    public MenuInfo getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }
}
