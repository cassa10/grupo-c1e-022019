package com.desapp.grupoc1e022019.persistence;

import com.desapp.grupoc1e022019.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByName(String name);
}
