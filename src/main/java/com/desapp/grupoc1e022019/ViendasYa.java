package com.desapp.grupoc1e022019;

import java.util.ArrayList;

public class ViendasYa {
    private ArrayList<Provider> providers;
    public ViendasYa(){
        providers = new ArrayList<>();
    }

    public ArrayList<Provider> getProviders() {
        return this.providers;
    }

    public void addProvider(Provider aNewProvider) {
        this.providers.add(aNewProvider);
    }
}
