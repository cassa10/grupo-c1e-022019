package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderDAO {

    @Autowired
    private ProviderRepository providerRepository;

    public void save(Provider provider){
        providerRepository.save(provider);
    }

}
