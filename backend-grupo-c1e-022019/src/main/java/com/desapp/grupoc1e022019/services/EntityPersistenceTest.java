package com.desapp.grupoc1e022019.services;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EntityPersistenceTest extends EntityId {

    private String name;

    public EntityPersistenceTest(Long id, String name){
        super(id);
        this.name = name;
    }
    public EntityPersistenceTest(){}

    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
