package com.desapp.grupoc1e022019.model.providerComponents.providerState;

import com.desapp.grupoc1e022019.model.Provider;

public class NormalProvider extends ProviderState {

    @Override
    public boolean isNormalProvider(){
        return true;
    }

    @Override
    public String toString(){
        return "NORMAL";
    }

    @Override
    public void addStrike(Provider provider){

        provider.setStrikesMenu(provider.getStrikesMenu() + 1);
        if(provider.getStrikesMenu() >= 10){
            provider.setProviderState(new PenalizedProvider());
            //TODO
            // SendEmailTLS.send(provider.getEmail(),"Penalization","You are penalized xd");
        }
    }
}
