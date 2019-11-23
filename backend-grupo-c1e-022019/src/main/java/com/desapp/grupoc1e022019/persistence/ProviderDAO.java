package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProviderDAO {

    @Autowired
    private ProviderRepository providerRepository;

    public Provider save(Provider provider){
        return providerRepository.save(provider);
    }

    public void delete(Long id) {
        providerRepository.deleteById(id);
    }

    public boolean providerExists(long idProvider) {
            return providerRepository.findById(idProvider).isPresent();
    }

    public Provider getProvider(long id) {
            return providerRepository.getOne(id);
    }

    public boolean existsProviderWithSameEmail(String email) {
        return ! providerRepository.findByEmail(email).isPresent();
    }

    public boolean existProviderByGoogleId(String googleId) {
        return providerRepository.existsByGoogleId(googleId);
    }

    public Optional<Provider> findByGoogleId(String googleId) {
        return providerRepository.findByGoogleId(googleId);
    }
}
