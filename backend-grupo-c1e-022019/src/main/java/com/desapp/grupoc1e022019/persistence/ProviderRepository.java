package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {
}
