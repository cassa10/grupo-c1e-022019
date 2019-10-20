package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;

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

    public String getName() {
        return name;
    }

    public MenuDTO(){}

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
}
