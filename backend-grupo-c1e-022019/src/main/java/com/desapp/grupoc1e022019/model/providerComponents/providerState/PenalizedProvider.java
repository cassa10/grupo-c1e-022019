package com.desapp.grupoc1e022019.model.providerComponents.providerState;

import com.desapp.grupoc1e022019.model.Provider;

import javax.persistence.Entity;

@Entity
public class PenalizedProvider extends ProviderState {

    @Override
    public boolean isPenalized(){
        return true;
    }

    @Override
    public String toString(){
        return "PENALIZED";
    }

    @Override
    public void addStrike(Provider provider){
        //NOTHING BECAUSE PROVIDER IS PENALIZED
    }
}
