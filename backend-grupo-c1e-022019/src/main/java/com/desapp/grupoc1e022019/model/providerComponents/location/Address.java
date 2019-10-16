package com.desapp.grupoc1e022019.model.providerComponents.location;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Address extends EntityId {
    @OneToOne
    private Coord coord;
    private String location;

    public Address(){}

    public Address(Coord coord, String location){
        this.setCoord(coord);
        this.setLocation(location);
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
