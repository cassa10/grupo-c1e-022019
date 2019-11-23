package com.desapp.grupoc1e022019.model.providerComponents.providerState;

import com.desapp.grupoc1e022019.model.Provider;

import javax.persistence.Entity;

@Entity
public class DeletingProcessProvider extends ProviderState {

    @Override
    public boolean isDeletingProcessProvider() {
        return true;
    }

    @Override
    public String toString() {
        return "DeletingProcess";
    }

    @Override
    public void addStrike(Provider provider) {
        provider.setProviderState(new NormalProvider());
        provider.setStrikesMenu(provider.getStrikesMenu() + 1);
        if(provider.getStrikesMenu() >= 10){
            provider.setProviderState(new PenalizedProvider());
            //TODO
            // SendEmailTLS.send(provider.getEmail(),"Penalization","You are penalized xd");
        }
    }
}
