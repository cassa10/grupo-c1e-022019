package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientDAO {

    @Autowired
    private ClientRepository clientRepository;

    public void save(Client client){
        clientRepository.save(client);
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }

    public Client getClient(long id){
        return clientRepository.getOne(id);
    }
}
