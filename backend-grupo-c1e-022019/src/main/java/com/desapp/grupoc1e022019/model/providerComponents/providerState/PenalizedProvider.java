package com.desapp.grupoc1e022019.model.providerComponents.providerState;

public class PenalizedProvider extends ProviderState {

    @Override
    public boolean isPenalized(){
        return true;
    }

    @Override
    public String toString(){
        return "PENALIZED";
    }
}
