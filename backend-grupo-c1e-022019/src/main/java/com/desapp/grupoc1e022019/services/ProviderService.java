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

    @Transactional
    public void save(Provider provider){
        providerDAO.save(provider);
    }

    @Transactional
    public void delete(Long id) {
        providerDAO.delete(id);
    }

    @Transactional
    public boolean providerExists(long idProvider) {
        return providerDAO.providerExists(idProvider);
    }

    public Provider getProvider(long idProvider) {
        return providerDAO.getProvider(idProvider);
    }
}
