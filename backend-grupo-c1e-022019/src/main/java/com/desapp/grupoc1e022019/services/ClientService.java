package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.GoogleToken;
import com.desapp.grupoc1e022019.persistence.ClientDAO;
import com.desapp.grupoc1e022019.persistence.GoogleTokenDAO;
import com.desapp.grupoc1e022019.services.dtos.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Scope(value = "session")
@Component(value = "clientService")
public class ClientService {

    @Autowired
    private ClientDAO clientDAO = new ClientDAO();

    @Autowired
    private GoogleTokenDAO googleTokenDAO = new GoogleTokenDAO();

    public boolean existSameClientEmail(String email) {
        return clientDAO.existSameClientEmail(email);
    }

    @Transactional
    public Client save(Client client) {
        return clientDAO.save(client);
    }

    public boolean clientExist(long idClient) {
        return clientDAO.clientExist(idClient);
    }

    public Client getClient(long idClient) {
        return clientDAO.getClient(idClient);
    }

    @Transactional
    public Client updateClientBasicInfo(Client clientRecovered, ClientDTO clientDTO) {

        clientRecovered.setAddress(clientDTO.getAddress());
        clientRecovered.setPhoneNumber(clientDTO.getPhoneNumber());
        clientRecovered.setFirstName(clientDTO.getFirstName());
        clientRecovered.setLastName(clientDTO.getLastName());
        clientRecovered.setLocation(clientDTO.getLocation());

        return clientDAO.save(clientRecovered);
    }

    @Transactional
    public Client buy(long id, double price) {
        Client tmp = clientDAO.getClient(id);

        tmp.debit(new Credit(price));

        clientDAO.save(tmp);
        return tmp;
    }

    @Transactional
    public Client accredit(long id, double amount) {
        Client tmp = clientDAO.getClient(id);

        tmp.deposit(new Credit(amount));

        clientDAO.save(tmp);
        return tmp;
    }

    public boolean existClientByGoogleId(String googleId) {
        return clientDAO.existClientByGoogleId(googleId);
    }

    @Transactional
    public Client saveClientAndGoogleAuth(Client client, GoogleToken googleAuth) {
        Client newClient = clientDAO.save(client);

        googleTokenDAO.save(googleAuth);

        return newClient;
    }

    public Optional<Client> findClientByGoogleId(String googleId) {
        return clientDAO.findClientByGoogleId(googleId);
    }

    public Optional<Client> findClientById(long id) {
        return clientDAO.findClientById(id);
    }
}
