package com.desapp.grupoc1e022019.Model.Location;

public class Address {
    private Coord coord;
    private String location;

    public Address(Coord coord, String location){
        this.coord = coord;
        this.location = location;
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
