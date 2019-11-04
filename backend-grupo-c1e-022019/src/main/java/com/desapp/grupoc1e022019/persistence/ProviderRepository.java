package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

    List<Provider> findByEmail(String email);

}
