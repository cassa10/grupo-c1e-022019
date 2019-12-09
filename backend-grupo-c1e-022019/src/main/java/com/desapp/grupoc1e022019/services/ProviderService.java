package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.CancelledMenu;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.DeletingProcessProvider;
import com.desapp.grupoc1e022019.persistence.MenuDAO;
import com.desapp.grupoc1e022019.persistence.ProviderDAO;
import com.desapp.grupoc1e022019.services.dtos.ProviderDTO;
import com.desapp.grupoc1e022019.services.dtos.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Scope(value = "session")
@Component(value = "providerService")
public class ProviderService {

    @Autowired
    private ProviderDAO providerDAO = new ProviderDAO();

    @Autowired
    private MenuDAO menuDAO = new MenuDAO();

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
    public Provider updateProviderBasicInfo(Provider providerRecovered,ProviderDTO providerDTO) {

        providerRecovered.setName(providerDTO.getName());
        providerRecovered.setLogo(providerDTO.getLogo());
        providerRecovered.setDeliveryMaxDistanceInKM(providerDTO.getDeliveryMaxDistanceInKM());
        providerRecovered.setDescription(providerDTO.getDescription());
        providerRecovered.setTelNumber(providerDTO.getTelNumber());
        providerRecovered.setWebURL(providerDTO.getWebURL());

        return providerDAO.save(providerRecovered);
    }

    public boolean existProviderWithSameEmail(String email) {
        return providerDAO.existsProviderWithSameEmail(email);
    }

    @Transactional
    public Provider withdrawCredit(Provider providerRecovered, Credit creditToWithdraw) {

        providerRecovered.withdrawCredit(creditToWithdraw);

        return providerDAO.save(providerRecovered);
    }

    public boolean existProviderByGoogleId(String googleId) {
        return providerDAO.findByGoogleId(googleId).isPresent();
    }

    public Optional<Provider> findProviderByGoogleId(String googleId) {
        return providerDAO.findByGoogleId(googleId);
    }

    @Transactional
    public Provider setProviderDeletingProcess(Provider providerRecovered) {

        providerRecovered.setProviderState(new DeletingProcessProvider());

        List<Menu> providerMenus = menuDAO.findAllByProvider(providerRecovered);

        menuDAO.cancelAndSaveMenus(providerMenus);

        providerDAO.save(providerRecovered);

        return providerRecovered;
    }

    @Transactional
    public Provider updateProviderSchedule(Provider providerRecovered, ScheduleDTO scheduleDTO) {

        providerRecovered.setSchedule(scheduleDTO.getSchedule());

        return providerDAO.save(providerRecovered);
    }

    public Optional<Provider> findProviderById(long providerId) {
        return providerDAO.findProviderById(providerId);
    }

    public double getProviderRankReputation(Provider provider) {
        return menuDAO.getProviderRankReputation(provider);
    }
}
