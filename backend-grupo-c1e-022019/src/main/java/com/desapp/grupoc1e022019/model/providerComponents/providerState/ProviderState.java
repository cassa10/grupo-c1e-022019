package com.desapp.grupoc1e022019.model.providerComponents.providerState;

public abstract class ProviderState {

    public boolean isPenalized(){
        return false;
    }

    public boolean isNormalProvider(){
        return false;
    }

    public abstract String toString();

}
