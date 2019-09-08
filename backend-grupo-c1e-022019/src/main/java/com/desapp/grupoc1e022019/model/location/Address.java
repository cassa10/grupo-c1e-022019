package com.desapp.grupoc1e022019.model.location;

public class Address {
    private Coord coord;
    private String location;

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
