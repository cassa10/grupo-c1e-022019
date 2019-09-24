package com.desapp.grupoc1e022019.model;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
