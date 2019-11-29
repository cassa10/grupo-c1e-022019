package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import org.junit.Assert;
import org.junit.Test;

public class CoordTest {

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude2dWhenCoordRecievesGetLatitudeThenItReturn0(){
        Coord coord = new Coord("0","2");
        Assert.assertEquals(coord.getLatitude(),"0");
    }

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude2dWhenCoordRecievesGetLongitudeThenItReturn2(){
        Coord coord = new Coord("0","2");
        Assert.assertEquals(coord.getLongitude(),"2");
    }

    @Test
    public void testGivenACoordinateWithLatitude5dAndLongitude0dWhenCoordRecievesGetLatitudeThenItReturn0(){
        Coord coord = new Coord("5","2");
        Assert.assertEquals(coord.getLatitude(),"5");
    }

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude0dWhenCoordRecievesGetLongitudeThenItReturn0(){
        Coord coord = new Coord("0","0");
        Assert.assertEquals(coord.getLongitude(),"0");
    }
}
