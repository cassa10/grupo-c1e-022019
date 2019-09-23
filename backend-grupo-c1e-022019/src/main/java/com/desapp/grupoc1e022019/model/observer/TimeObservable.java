package com.desapp.grupoc1e022019.model.observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeObservable {
    //TODO
    //  HACER ESTO DESDE UN SERVICE CON QUERYS PARA NO RECORRER TODOS LOS PEDIDOS Y DESPUES NO TENER EL PROBLEMA DE
    //  COMO SACARLOS DE LA LISTA DE OBSERVERS.
    private List<Observer> observers;

    public  TimeObservable(){
        this.observers = new ArrayList<>();
    }

    public void attach(Observer obs){
        this.observers.add(obs);
    }

    public void notifyObserversWithTime(LocalDateTime date) {

        if (date.getHour() == 0) {
            observers.forEach(observer -> observer.update());
            observers = new ArrayList<>();
        }
    }

}
