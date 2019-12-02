package com.desapp.grupoc1e022019.services.dtos;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;

import java.time.LocalDate;
import java.util.List;

public class MenuDTO {

    private long idProvider;
    private long id;
    private String tokenAccess;
    private String googleId;
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

    public boolean formMenuIsValid() {
        return isValidName() && isValidDescription() && isValidCategories() &&
                isValidDeliveryValue() && isValidEffectiveDate() && isValidMaxSalesPerDay() &&
                isValidAverageDeliveryTimeInMinutes() && isValidMenuPriceCalculator();
    }

    private boolean isValidMenuPriceCalculator() {
        return menuPriceCalculator != null && isValidPriceInMenuPriceCalculator();
    }

    private boolean isValidPriceInMenuPriceCalculator() {
        boolean mustHave = menuPriceCalculator.getPrice() != null &&
                menuPriceCalculator.getFirstMinAmountPrice() != null &&
                menuPriceCalculator.getFirstMinAmount() != null;

        if(menuPriceCalculator.getSecondMinAmountPrice() != null &&
                menuPriceCalculator.getSecondMinAmount() != null) {

            return mustHave && checkAllPriceCalculatorPricesAndAmountsValid();
        }

        return mustHave && checkPriceAndFstPriceOnlyValid();
    }

    private boolean checkPriceAndFstPriceOnlyValid() {
        boolean checkValidPrices = menuPriceCalculator.getPrice() > menuPriceCalculator.getFirstMinAmountPrice() &&
                                    menuPriceCalculator.getFirstMinAmountPrice() > 0;

        return checkValidPrices && menuPriceCalculator.getFirstMinAmount() > 1 ;
    }

    private boolean checkAllPriceCalculatorPricesAndAmountsValid() {

        boolean checkValidPrices = menuPriceCalculator.getPrice() > menuPriceCalculator.getFirstMinAmountPrice() &&
                            menuPriceCalculator.getFirstMinAmountPrice() > menuPriceCalculator.getSecondMinAmountPrice() &&
                            menuPriceCalculator.getSecondMinAmountPrice() > 0;

        boolean checkValidAmounts = menuPriceCalculator.getSecondMinAmount() > menuPriceCalculator.getFirstMinAmount() &&
                                     menuPriceCalculator.getFirstMinAmount() > 1 ;

        return checkValidPrices && checkValidAmounts;
    }

    private boolean isValidAverageDeliveryTimeInMinutes() {
        return averageDeliveryTimeInMinutes != null && averageDeliveryTimeInMinutes > 0;
    }

    private boolean isValidMaxSalesPerDay() {
        //VALUE MAKE UP: maxsalesperday > 3
        return maxSalesPerDay != null && maxSalesPerDay > 3;
    }

    private boolean isValidEffectiveDate() {
        return effectiveDate != null && effectiveDate.getValidFrom().isBefore(effectiveDate.getGoodThru())

                && (effectiveDate.getGoodThru().isAfter(LocalDate.now()));
    }


    private boolean isValidDeliveryValue() {
        // VALUES OF DOCUMENT (PDF)
        // delivery value == 0 means free delivery value
        return deliveryValue != null && (deliveryValue == 0 || (deliveryValue >= 10 && deliveryValue <= 40));
    }

    private boolean isValidCategories() {
        // VALUES OF DOCUMENT (PDF)
        return categories != null && categories.size() >= 1;
    }

    private boolean isValidDescription() {
        // VALUES OF DOCUMENT (PDF)
        return description != null && 20 <= description.trim().length() && description.trim().length() <= 40;
    }

    private boolean isValidName() {
        // VALUES OF DOCUMENT (PDF)
        return name != null && 4 <= name.trim().length() && name.trim().length() <= 30;
    }


    @Override
    public String toString(){

        return "{ "+"id: "+ id +" ,idProvider: "+ this.idProvider + " ,googleId: " +this.googleId + " ,accessToken: "+this.tokenAccess
                + " ,name: " +this.name + " ,description: "+this.description +" ,categories: " +this.categories  +
                " ,deliveryValue: "+ this.deliveryValue + " ,effectiveDate: " +
                this.effectiveDate + " ,averageDeliveryTimeInMinutes: "+ this.averageDeliveryTimeInMinutes
                + " ,maxSalesPerDay: " +this.maxSalesPerDay+ " ,menuPriceCalculator: "+ this.menuPriceCalculator +" }";
    }

    public void trimAllStringValues() {
        description = description.trim();
        name = name.trim();
    }

    public boolean existMenuWithSameName(List<Menu> menusOfProvider) {
        return menusOfProvider.stream().filter(m -> ! m.isCancelledState()).anyMatch(m -> m.getName().equals(name));
    }
}
