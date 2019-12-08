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

    public DeliverType parseDeliveryType() {
        DeliverType value;
        if(isPickUpType()){
            value = new PickUp(deliverDate);
        }else{
            value = new Delivery(deliverDate,destination);
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

    public boolean formIsValid() {

        return hasValidMenusAmount() && hasValidDeliverType() &&
                hasValidDestination() && hasValidDeliverDate();
    }

    private boolean hasValidDeliverDate() {
        return deliverDate != null && deliverDate.isAfter(LocalDateTime.now().plusHours(48)) &&
                    deliverDate.isBefore(LocalDateTime.now().plusDays(15));
    }

    private boolean hasValidDestination() {
        if (isPickUpType()) {
            return true;
        }
        return destination != null && destination.isValid();
    }

    private boolean hasValidMenusAmount() {
        return menusAmount != null && menusAmount > 0;
    }

    private boolean isDeliveryType(){
        return this.deliverType.trim().equals("delivery");
    }

    private boolean isPickUpType(){
        return deliverType.trim().equals("pickup");
    }

    private boolean hasValidDeliverType() {

        return deliverType != null && (isPickUpType() || isDeliveryType());
    }

}
