package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client,Long> {
}
