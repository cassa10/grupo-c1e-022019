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

    public abstract String toString();

    public abstract void addStrike(Provider provider);
}
