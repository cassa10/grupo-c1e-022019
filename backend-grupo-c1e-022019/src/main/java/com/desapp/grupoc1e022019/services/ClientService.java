package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.persistence.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "clientService")
public class ClientService {

    @Autowired
    private ClientDAO clientDAO = new ClientDAO();
}
