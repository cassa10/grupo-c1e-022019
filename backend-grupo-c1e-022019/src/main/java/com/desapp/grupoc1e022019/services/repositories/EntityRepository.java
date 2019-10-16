package com.desapp.grupoc1e022019.services.repositories;

import com.desapp.grupoc1e022019.services.EntityPersistenceTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<EntityPersistenceTest,Long> {

}
