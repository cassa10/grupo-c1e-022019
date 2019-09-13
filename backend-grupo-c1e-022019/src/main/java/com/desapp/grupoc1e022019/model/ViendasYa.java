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

    public static class Average {
        Double total;
        Integer ratesAmount;
        public Average(Double total) {
            this.total = total;
            ratesAmount = 0;
        }

        public void rate(Integer score) {
            total+=score;
            ratesAmount++;
        }

        public Double getStars() {
            if(ratesAmount != 0){
                return (double)total / ratesAmount;
            }
            return new Double(0);
        }
    }
}
