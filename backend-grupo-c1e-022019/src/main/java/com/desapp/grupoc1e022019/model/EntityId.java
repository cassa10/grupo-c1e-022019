package com.desapp.grupoc1e022019.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public class EntityId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    public EntityId(){
    }

    public EntityId(long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    @Transient
    public int getIdAsInt(){
        return (int) id;
    }

    public void setId(long id){
        this.id = id;
    }
}
