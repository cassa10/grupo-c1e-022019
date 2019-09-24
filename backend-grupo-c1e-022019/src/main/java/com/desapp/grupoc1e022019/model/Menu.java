package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.MenuState;

import java.time.LocalDate;
import java.util.List;

public class Menu extends Entity {

    private Provider provider;
    private String name;
    private String description;
    private List<CategoryMenu> categories;
    private Double deliveryValue;
    private EffectiveDate effectiveDate;
    private Integer averageDeliveryTimeInMinutes;
    private MenuPriceCalculator menuPriceCalculator;
    private Integer maxSalesPerDay;
    //TODO
    // FALTA EL Turno/Horario de entrega/envio que no entiendo que funcion cumpliria
    private RankAverageMenu menuRank;
    private MenuState menuState;

    public Menu(int id,Provider provider, String name,String description,List<CategoryMenu> categories,
                Double deliveryValue, EffectiveDate effectiveDate,Integer averageDeliveryTimeInMinutes,
                Integer maxSalesPerDay, MenuPriceCalculator menuPriceCalculator, RankAverageMenu menuRank,
                MenuState menuState) {
        this.setId(id);
        this.provider = provider;
        this.setName(name);
        this.setDescription(description);
        this.setCategories(categories);
        this.setDeliveryValue(deliveryValue);
        this.setEffectiveDate(effectiveDate);
        this.setAverageDeliveryTimeInMinutes(averageDeliveryTimeInMinutes);
        this.setMaxSalesPerDay(maxSalesPerDay);
        this.menuPriceCalculator = menuPriceCalculator;
        this.menuRank = menuRank;
        this.setMenuState(menuState);
    }

    public Double getPrice(){
        return menuPriceCalculator.getPrice();
    }

    public void setPrice(Double price){
        menuPriceCalculator.setPrice(price);
    }
    public Integer getFirstMinAmount(){
        return menuPriceCalculator.getFirstMinAmount();
    }

    public void setFirstMinAmount(Integer firstMinAmount) {
        menuPriceCalculator.setFirstMinAmount(firstMinAmount);
    }

    public Double getFirstMinAmountPrice() {
        return menuPriceCalculator.getFirstMinAmountPrice();
    }

    public void setFirstMinAmountPrice(Double firstMinAmountPrice) {
        menuPriceCalculator.setFirstMinAmountPrice(firstMinAmountPrice);
    }

    public Integer getSecondMinAmount() {
        return menuPriceCalculator.getSecondMinAmount();
    }

    public void setSecondMinAmount(Integer secondMinAmount) {
        menuPriceCalculator.setSecondMinAmount(secondMinAmount);
    }

    public Double getSecondMinAmountPrice() {
        return menuPriceCalculator.getSecondMinAmountPrice();
    }

    public void setSecondMinAmountPrice(Double secondMinAmountPrice) {
        menuPriceCalculator.setSecondMinAmountPrice(secondMinAmountPrice);
    }

    public Provider getProvider() {
        return provider;
    }

    public String getName() {
        return name;
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

    public void setMenuState(MenuState menuState){
        this.menuState = menuState;
    }

    public LocalDate getEffectiveDateValidFrom(){
        return effectiveDate.getValidFrom();
    }

    public LocalDate getEffectiveDateGoodThru(){
        return effectiveDate.getGoodThru();
    }

    public Double priceWithAmount(Integer amount){
        return menuPriceCalculator.calculateMenuPrice(amount);
    }

    public void addRate(Integer score){
        this.menuState.addRate(this,menuRank,score);
    }

    public void penalizeProvider(){
        provider.addAStrike();
        notifyToProviderMenuCancelled();
    }

    public void notifyToProviderMenuCancelled() {
        //TODO
        // PONER MAIL SENDER ACA AL PROVIDER QUE EL MENU FUE CANCELADO
    }

    public boolean reachMaxSalesToday(){
        //TODO
        //  UTILIZAR SERVICE Y UNA QUERY DE BASE DE DATOS (COUNT) CON LA FECHA DE HOY Y EL ID DEL MENU
        return false;
    }

    public boolean todayIsBeingAnEffectiveDate(){
        return effectiveDate.todayIsBeingAnEffectiveDate();
    }

}
