package com.desapp.grupoc1e022019.model.builder;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderState.OrderState;
import com.desapp.grupoc1e022019.model.orderState.PendingOrder;

public class OrderBuilder {
    private OrderState state = new PendingOrder();
    private Integer stars = 0;
    private Client client = new ClientBuilder().aClient().build();
    private Menu menu;
    private Integer menusAmount;
    private DeliverType deliverType;

    public static OrderBuilder anOrder(){
        return new OrderBuilder();
    }

    public OrderBuilder withState(OrderState orderState){
        state = orderState;
        return this;
    }

    public OrderBuilder withClient(Client client){
        this.client = client;
        return this;
    }

    public OrderBuilder withMenu(Menu menu){
        this.menu = menu;
        return this;
    }

    public OrderBuilder withMenusAmount(Integer menusAmount){
        this.menusAmount = menusAmount;
        return this;
    }

    public OrderBuilder withDeliverType(DeliverType deliverType){
        this.deliverType = deliverType;
        return this;
    }

    public OrderBuilder withStars(Integer stars){
        this.stars = stars;
        return this;
    }

    public Order build() {
        return new Order(state,stars,client,menu,menusAmount,deliverType);
    }

}
