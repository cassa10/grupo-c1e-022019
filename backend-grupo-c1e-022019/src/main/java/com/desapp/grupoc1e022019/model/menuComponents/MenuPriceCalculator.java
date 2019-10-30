package com.desapp.grupoc1e022019.model.menuComponents;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public class MenuPriceCalculator extends EntityId {

    private Double price;
    private Integer firstMinAmount;
    private Double firstMinAmountPrice;
    private Integer secondMinAmount;
    private Double secondMinAmountPrice;

    public MenuPriceCalculator(){}

    public MenuPriceCalculator(Double price, Integer firstMinAmount,Double firstMinAmountPrice,
                               Integer secondMinAmount,Double secondMinAmountPrice){

        this.setPrice(price);
        this.setFirstMinAmount(firstMinAmount);
        this.setFirstMinAmountPrice(firstMinAmountPrice);
        this.setSecondMinAmount(secondMinAmount); ;
        this.setSecondMinAmountPrice(secondMinAmountPrice);
    }

    public Double calculateCurrentMenuPrice(Integer amount) {
        //TODO
        // DUDA DE ENUNCIADO (RELACION ENTRE EL MIN Y EL AMOUNT O CANTIDAD DE VENTAS)
        double value;
        if(amount >= secondMinAmount){
            value = secondMinAmountPrice * amount;
        }else{
            if(amount >= firstMinAmount){
                value = firstMinAmountPrice * amount;
            }
            else{
                value = price * amount;
            }
        }
        return value;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFirstMinAmount() {
        return firstMinAmount;
    }

    public void setFirstMinAmount(Integer firstMinAmount) {
        this.firstMinAmount = firstMinAmount;
    }

    public Double getFirstMinAmountPrice() {
        return firstMinAmountPrice;
    }

    public void setFirstMinAmountPrice(Double firstMinAmountPrice) {
        this.firstMinAmountPrice = firstMinAmountPrice;
    }

    public Integer getSecondMinAmount() {
        return secondMinAmount;
    }

    public void setSecondMinAmount(Integer secondMinAmount) {
        this.secondMinAmount = secondMinAmount;
    }

    public Double getSecondMinAmountPrice() {
        return secondMinAmountPrice;
    }

    public void setSecondMinAmountPrice(Double secondMinAmountPrice) {
        this.secondMinAmountPrice = secondMinAmountPrice;
    }
}
