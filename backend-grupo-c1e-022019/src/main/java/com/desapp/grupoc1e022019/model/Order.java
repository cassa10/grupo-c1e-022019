package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.orderComponents.MenuInfo;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.OrderState;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends EntityId {

    private Integer stars;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;
    private Integer menusAmount;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_deliver_type")
    private DeliverType deliverType;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_order_state")
    private OrderState orderState;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu")
    @JsonBackReference
    private Menu menu;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "id_menu_info")
    private MenuInfo menuInfo;

    public Order(){}

    public Order(OrderState orderState, Integer stars, Client client, Menu menu, Integer menusAmount, DeliverType deliverType) {
        this.stars = stars;
        this.orderState = orderState;
        this.client = client;
        this.menu = menu;
        this.menusAmount = menusAmount;
        this.deliverType = deliverType;
        this.menuInfo = new MenuInfo(menu);
    }

    public Integer getStars() {
        return this.stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void confirmed(){
        this.orderState.confirm(this);
    }

    public void cancelled() {
        orderState.cancelled(this) ;
    }

    public void delivered() {
        orderState.delivered(this);
    }

    public void sending() {
        this.orderState.sending(this);
    }

    public void rate(Integer score) {
        orderState.rate(score,this);
    }

    public Client getClient(){
        return this.client;
    }

    public Menu getMenu(){ return this.menu;}

    public boolean isStateCancelled(){return this.orderState.isStateCancelled();}

    public boolean isStateConfirmed(){return this.orderState.isStateConfirmed();}

    public boolean isStateDelivered(){return this.orderState.isStateDelivered();}

    public boolean isStatePending(){return this.orderState.isStatePending();}

    public boolean isStateSending(){return this.orderState.isStateSending();}

    public boolean isStateRanked(){return this.orderState.isStateRanked();}

    public String getStateName(){
        return orderState.toString();
    }

    public boolean isDelivery(){
        return deliverType.isDelivery();
    }

    public boolean isPickUpDeliver(){
        return deliverType.haveToPickUp();
    }

    public Credit customerPayment(){ return new Credit(menu.priceWithAmount(menusAmount));}

    public Double getOrderPrice(){
        return menu.priceWithAmount(this.menusAmount);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getMenusAmount() {
        return menusAmount;
    }

    public void setMenusAmount(Integer menusAmount) {
        this.menusAmount = menusAmount;
    }

    public DeliverType getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(DeliverType deliverType) {
        this.deliverType = deliverType;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public long getIdClient(){
        return this.client.getId();
    }

    public long getIdMenu(){
        return this.menu.getId();
    }

    public long getIdProviderOfMenu(){
        return this.menu.getProviderId();
    }

    public int getDeliverTimeAverageInMinutes(){
        return deliverType.getDeliverTimeAverageInMinutes(menu.getAverageDeliveryTimeInMinutes(),menu.getDeliveryMaxDistanceInKM());
    }

    public Address getOrderDestinationAddress(){
        return deliverType.getDestinationOrder(this);
    }

    public LocalDateTime getDeliverDate() {return deliverType.getDeliverDate();}

    public String getMenuName(){
        return menu.getName();
    }

    public MenuInfo getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }
}
