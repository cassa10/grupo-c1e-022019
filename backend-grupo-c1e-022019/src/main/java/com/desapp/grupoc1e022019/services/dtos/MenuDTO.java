package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;

import java.util.List;

public class MenuDTO {

    private long idProvider;
    private String name;
    private String description;
    private List<CategoryMenu> categories;
    private Double deliveryValue;
    private EffectiveDate effectiveDate;
    private Integer averageDeliveryTimeInMinutes;
    private Integer maxSalesPerDay;
    private MenuPriceCalculator menuPriceCalculator;

    public String getName() {
        return name;
    }

    public MenuDTO(){}

    public MenuDTO(Menu menu){
        idProvider = menu.getProvider().getId();
        name = menu.getName();
        description = menu.getDescription();
        categories = menu.getCategories();
        deliveryValue = menu.getDeliveryValue();
        effectiveDate = menu.getEffectiveDate();
        averageDeliveryTimeInMinutes = menu.getAverageDeliveryTimeInMinutes();
        maxSalesPerDay = menu.getMaxSalesPerDay();
        menuPriceCalculator = getMenuPriceCalculator();
    }

    public long getIdProvider() {
        return idProvider;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryMenu> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryMenu> categories) {
        this.categories = categories;
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

    public Integer getMaxSalesPerDay() {
        return maxSalesPerDay;
    }

    public void setMaxSalesPerDay(Integer maxSalesPerDay) {
        this.maxSalesPerDay = maxSalesPerDay;
    }

    public void setIdProvider(long idProvider) {
        this.idProvider = idProvider;
    }

    public MenuPriceCalculator getMenuPriceCalculator() {
        return menuPriceCalculator;
    }

    public void setMenuPriceCalculator(MenuPriceCalculator menuPriceCalculator) {
        this.menuPriceCalculator = menuPriceCalculator;
    }
}
