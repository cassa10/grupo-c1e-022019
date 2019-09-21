package com.desapp.grupoc1e022019.model.observer;

import java.util.ArrayList;

public class TimeObservable  {

    private ArrayList<Observer> observers;

    public  TimeObservable(){
        this.observers = new ArrayList<Observer>();
    }
    public void attach(Observer obs){
        this.observers.add(obs);
    }
    public void updateObservers(){
        observers.forEach(observer -> observer.update());
    }
}
