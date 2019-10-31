package com.desapp.grupoc1e022019.model.providerComponents.location;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public class Coord extends EntityId {

    private Double latitude;
    private Double longitude;

    public Coord(Double latitude, Double longitude){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    public Coord(){}

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
