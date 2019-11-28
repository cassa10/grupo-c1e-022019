package com.desapp.grupoc1e022019.model.providerComponents.providerState;

import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Provider;

import javax.persistence.Entity;

@Entity
public abstract class ProviderState  extends EntityId{

    public boolean isPenalized(){
        return false;
    }

    public boolean isNormalProvider(){
        return false;
    }

    public boolean isDeletingProcessProvider() {return false;}

    public abstract void addStrike(Provider provider);

    public void setDeletingProcessProviderState(Provider provider){
        if(provider.getStrikesMenu() == 0){
            provider.setProviderState(new DeletingProcessProvider());
        }
    }

    @Override
    public abstract String toString();
}
