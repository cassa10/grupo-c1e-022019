package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientDAO {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }

    public Client getClient(long id){
        return clientRepository.getOne(id);
    }

    public boolean existSameClientEmail(String email) {
        return ! clientRepository.findByEmail(email).isEmpty();
    }

    public boolean clientExist(long idClient) {
        return clientRepository.findById(idClient).isPresent();
    }

    public boolean existClientByGoogleId(String googleId) {
        return clientRepository.existsByGoogleId(googleId);
    }
}
