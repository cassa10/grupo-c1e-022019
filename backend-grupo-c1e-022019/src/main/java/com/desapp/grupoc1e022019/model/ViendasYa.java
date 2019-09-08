package com.desapp.grupoc1e022019.model;

import java.util.ArrayList;
import java.util.List;

public class ViendasYa {
    private List<Provider> providers;
    public ViendasYa(){
        providers = new ArrayList<>();
    }

    public List<Provider> getProviders() {
        return this.providers;
    }

    public void addProvider(Provider aNewProvider) {
        this.providers.add(aNewProvider);
    }
}
