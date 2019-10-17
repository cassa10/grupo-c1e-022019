package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.services.EntityPersistencePilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityPersistencePilotRepository extends JpaRepository<EntityPersistencePilot,Long> {

}
