package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.DeliverType;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.Delivery;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.OrderState;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.PendingOrder;

public class OrderBuilder {
    private OrderState state = new PendingOrder();
    private Client client = ClientBuilder.aClient().build();
    private Menu menu = MenuBuilder.aMenu().build();
    private Integer menusAmount;
    private DeliverType deliverType= new Delivery();

    public static OrderBuilder anOrder(){
        return new OrderBuilder();
    }

    public Order build() {
        Integer stars = 0;
        return new Order(state, stars,client,menu,menusAmount,deliverType);
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

}
