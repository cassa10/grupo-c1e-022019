package com.desapp.grupoc1e022019.services.builder;

import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.MenuState;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.NormalMenu;
import com.desapp.grupoc1e022019.model.Provider;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {
    private int id = 0;
    private Provider provider = new ProviderBuilder().build();
    private String name;
    private String description;
    private List<CategoryMenu> categories = new ArrayList<>();
    private Double deliveryValue;
    private EffectiveDate effectiveDate;
    private Integer averageDeliveryTimeInMinutes;
    private MenuPriceCalculator menuPriceCalculator;
    private Integer maxSalesPerDay;
    private RankAverageMenu menuRank = new RankAverageMenu();
    private MenuState menuState = new NormalMenu();

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public Menu build() {
        return new Menu(this.nextId(),provider,name,description,
                categories,deliveryValue,effectiveDate,
                averageDeliveryTimeInMinutes,maxSalesPerDay,
                menuPriceCalculator,menuRank,menuState);
    }

    public MenuBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public MenuBuilder withName(String name){
        this.name = name;
        return this;
    }

    public MenuBuilder withDescription(String desc){
        description = desc;
        return this;
    }

    public MenuBuilder withCategories(List<CategoryMenu> categories){
        this.categories = categories;
        return this;
    }

    public MenuBuilder withDeliveryValue(Double deliveryValue){
        this.deliveryValue = deliveryValue;
        return this;
    }

    public MenuBuilder withEffectiveDate(EffectiveDate effectiveDate){
        this.effectiveDate = effectiveDate;
        return this;
    }

    public MenuBuilder withAverageDeliveryTimeInMinutes(Integer averageDeliveryTimeInMinutes){
        this.averageDeliveryTimeInMinutes = averageDeliveryTimeInMinutes;
        return this;
    }

    public MenuBuilder withMenuPriceCalculator(MenuPriceCalculator menuPriceCalculator){
        this.menuPriceCalculator = menuPriceCalculator;
        return this;
    }

    public MenuBuilder withMaxSalesPerDay(Integer maxSalesPerDay){
        this.maxSalesPerDay = maxSalesPerDay;
        return this;
    }

    public MenuBuilder withMenuState(MenuState menuState){
        this.menuState = menuState;
        return this;
    }

    private int nextId(){
        id = id++;
        return id;
    }
}
