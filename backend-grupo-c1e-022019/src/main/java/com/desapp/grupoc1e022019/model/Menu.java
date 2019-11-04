package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.MenuState;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Menu extends EntityId {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_provider")
    private Provider provider;
    private String name;
    private String description;
    @ElementCollection
    private List<CategoryMenu> categories;
    private Double deliveryValue;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_effective_date")
    private EffectiveDate effectiveDate;
    private Integer averageDeliveryTimeInMinutes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_menu_price_calculator")
    private MenuPriceCalculator menuPriceCalculator;
    private Integer maxSalesPerDay;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_menu_rank")
    private RankAverageMenu menuRank;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_menu_state")
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

    public Menu(){}

    public long getProviderId(){ return this.provider.getId();}

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
        return menuPriceCalculator.calculateCurrentMenuPrice(amount);
    }

    public void penalizeProvider(){
        provider.addAStrike();
        notifyToProviderMenuCancelled();
    }

    public void notifyToProviderMenuCancelled() {
        //TODO
        // PONER MAIL SENDER ACA AL PROVIDER QUE EL MENU FUE CANCELADO
    }

    //TODO
    //    UTILIZAR SERVICE Y UNA QUERY DE BASE DE DATOS (COUNT) CON LA FECHA DE HOY Y EL ID DEL MENU
    //    public boolean reachMaxSalesToday(){return null;}


    public boolean todayIsBeingAnEffectiveDate(){
        return effectiveDate.todayIsBeingAnEffectiveDate();
    }

    public Double getRankAverage(){
        return menuRank.average();
    }

    public boolean isNormalState(){
        return menuState.isNormal();
    }

    public boolean isCancelledState(){
        return menuState.isCancelled();
    }

    public void addRate(Integer score){
        this.menuState.addRate(this,menuRank,score);
    }

    public String getMenuStateName(){
        return menuState.toString();
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public EffectiveDate getEffectiveDate() {
        return effectiveDate;
    }

    public MenuPriceCalculator getMenuPriceCalculator() {
        return menuPriceCalculator;
    }

    public void setMenuPriceCalculator(MenuPriceCalculator menuPriceCalculator) {
        this.menuPriceCalculator = menuPriceCalculator;
    }

    public RankAverageMenu getMenuRank() {
        return menuRank;
    }

    public void setMenuRank(RankAverageMenu menuRank) {
        this.menuRank = menuRank;
    }

    public MenuState getMenuState() {
        return menuState;
    }

}
