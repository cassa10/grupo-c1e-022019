package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
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
    public Provider save(Provider provider){
        return providerDAO.save(provider);
    }

    @Transactional
    public void delete(Long id) {
        providerDAO.delete(id);
    }

    public boolean providerExists(long idProvider) {
        return providerDAO.providerExists(idProvider);
    }

    public Provider getProvider(long idProvider) {
        return providerDAO.getProvider(idProvider);
    }

    @Transactional
    public Provider updateProviderBasicInfo(ProviderDTO providerDTO) {
        Provider tmp = providerDAO.getProvider(providerDTO.getId());

        tmp.setName(providerDTO.getName());
        tmp.setLogo(providerDTO.getLogo());
        tmp.setAddress(providerDTO.getAddress());
        tmp.setCity(providerDTO.getCity());
        tmp.setDeliveryMaxDistanceInKM(providerDTO.getDeliveryMaxDistanceInKM());
        tmp.setDescription(providerDTO.getDescription());
        tmp.setTelNumber(providerDTO.getTelNumber());
        tmp.setWebURL(providerDTO.getWebURL());

        return providerDAO.save(tmp);
    }

    public boolean existProviderWithSameEmail(String email) {
        return providerDAO.existsProviderWithSameEmail(email);
    }

    @Transactional
    public void withdrawCredit(long idProvider, Credit creditToWithdraw) {
        Provider provider = providerDAO.getProvider(idProvider);

        provider.withdrawCredit(creditToWithdraw);

        providerDAO.save(provider);
    }
}
