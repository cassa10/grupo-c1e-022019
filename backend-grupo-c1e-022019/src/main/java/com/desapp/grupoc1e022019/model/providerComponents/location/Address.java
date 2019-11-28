package com.desapp.grupoc1e022019.model.providerComponents.location;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Address extends EntityId {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_COORD")
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

    public boolean isValid() {
        return coord != null && location != null && ! location.trim().equals("") && coord.isValid();
    }
}
