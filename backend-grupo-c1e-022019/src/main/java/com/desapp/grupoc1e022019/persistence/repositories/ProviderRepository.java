package com.desapp.grupoc1e022019.persistence.repositories;

import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

    Optional<Provider> findByEmail(String email);

    boolean existsByGoogleId(String googleId);

    Optional<Provider> findByGoogleId(String googleId);
}
