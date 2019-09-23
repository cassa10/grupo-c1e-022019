package com.desapp.grupoc1e022019.model;

public class Menu {
    private Integer id;
    //TODO
    //  TODAS LAS COSAS DEL MENU
    //  private Integer maxPreparationTime; (in minutes) para calcular cuanto tarda el delivery
    //  private StateMenu ; (Si el menu acumula 20 puntuaciones con promedio menos a 2, chau menu) THIS USE MAIL SENDER

    public Menu(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }

    public Double priceWithAmount(Integer amount){
        //TODO REFACTOR AND TEST
        return null;
    }
}
