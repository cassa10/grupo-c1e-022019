package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.observer.Observer;
import com.desapp.grupoc1e022019.model.observer.TimeObservable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ViendasYa {
    private List<Provider> providers;
    private TimeObservable observer;

    public ViendasYa(){
        providers = new ArrayList<>();
        observer = new TimeObservable();
    }

    public List<Provider> getProviders() {
        return this.providers;
    }

    public void addProvider(Provider aNewProvider) {
        this.providers.add(aNewProvider);
    }

    public void attachToObserver(Observer obs){
        observer.attach(obs);
    }

    public void notifyOrders(LocalDateTime date){
        observer.notifyObserversWithTime(date);
    }

}
