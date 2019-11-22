package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findByEmail(String email);

    boolean existsByGoogleId(String googleId);

    Optional<Client> findByGoogleId(String googleId);
}
