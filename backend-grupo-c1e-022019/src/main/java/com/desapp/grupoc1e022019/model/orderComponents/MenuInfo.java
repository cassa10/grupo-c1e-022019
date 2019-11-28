package com.desapp.grupoc1e022019.model.orderComponents;

import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.MenuState;

import javax.persistence.Entity;

@Entity
public class MenuInfo extends EntityId {

    private Double deliveryValue;
    private EffectiveDate effectiveDate;
    private Integer averageDeliveryTimeInMinutes;
    private MenuPriceCalculator menuPriceCalculator;
    private Integer maxSalesPerDay;
    private MenuState menuState;

    public MenuInfo(){}

    public MenuInfo(Menu menu){
        setDeliveryValue(menu.getDeliveryValue());
        setEffectiveDate(new EffectiveDate(menu.getEffectiveDateValidFrom(),menu.getEffectiveDateGoodThru()));
        setAverageDeliveryTimeInMinutes(menu.getAverageDeliveryTimeInMinutes());
        setMenuPriceCalculator(new MenuPriceCalculator(menu.getPrice(),menu.getFirstMinAmount(),menu.getFirstMinAmountPrice(),menu.getSecondMinAmount(),menu.getSecondMinAmountPrice()));
        setMaxSalesPerDay(menu.getMaxSalesPerDay());
        setMenuState(menu.getMenuState());
    }

    public Double getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(Double deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public EffectiveDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(EffectiveDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getAverageDeliveryTimeInMinutes() {
        return averageDeliveryTimeInMinutes;
    }

    public void setAverageDeliveryTimeInMinutes(Integer averageDeliveryTimeInMinutes) {
        this.averageDeliveryTimeInMinutes = averageDeliveryTimeInMinutes;
    }

    public MenuPriceCalculator getMenuPriceCalculator() {
        return menuPriceCalculator;
    }

    public void setMenuPriceCalculator(MenuPriceCalculator menuPriceCalculator) {
        this.menuPriceCalculator = menuPriceCalculator;
    }

    public Integer getMaxSalesPerDay() {
        return maxSalesPerDay;
    }

    public void setMaxSalesPerDay(Integer maxSalesPerDay) {
        this.maxSalesPerDay = maxSalesPerDay;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public Double getMenuPriceWithAmount(Integer amount){
        return menuPriceCalculator.calculateCurrentMenuPrice(amount);
    }
}
