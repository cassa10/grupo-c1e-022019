package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Scope(value = "session")
@Component(value = "providerService")
public class ProviderService {

    @Autowired
    private ProviderDAO providerDAO = new ProviderDAO();

    //TODO
    // USE @TRANSACTIONAL IN METHODS BUT USE JAVAX OR SPRING MODULE???

    @Transactional
    public void save(Provider provider){
        providerDAO.save(provider);
    }
}
