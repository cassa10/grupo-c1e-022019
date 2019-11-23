package com.desapp.grupoc1e022019.model.providerComponents.location;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public class Coord extends EntityId {

    private String latitude;
    private String longitude;

    public Coord(String latitude, String longitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    public Coord(){}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isValid() {
        return !latitude.trim().equals("") && !longitude.trim().equals("");
    }
}
