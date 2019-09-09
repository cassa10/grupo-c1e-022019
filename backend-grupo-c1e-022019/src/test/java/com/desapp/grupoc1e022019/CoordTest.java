package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.location.Coord;
import org.junit.Assert;
import org.junit.Test;

public class CoordTest {

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude2dWhenCoordRecievesGetLatitudeThenItReturnADoubleWithValue0d(){
        Coord coord = new Coord(0d,2d);
        Assert.assertEquals(coord.getLatitude(),new Double(0d));
    }

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude2dWhenCoordRecievesGetLongitudeThenItReturnADoubleWithValue2d(){
        Coord coord = new Coord(0d,2d);
        Assert.assertEquals(coord.getLongitude(),new Double(2d));
    }

    @Test
    public void testGivenACoordinateWithLatitude5dAndLongitude0dWhenCoordRecievesGetLatitudeThenItReturnADoubleWithValue0d(){
        Coord coord = new Coord(5d,2d);
        Assert.assertEquals(coord.getLatitude(),new Double(5d));
    }

    @Test
    public void testGivenACoordinateWithLatitude0dAndLongitude0dWhenCoordRecievesGetLongitudeThenItReturnADoubleWithValue2d(){
        Coord coord = new Coord(0d,0d);
        Assert.assertEquals(coord.getLongitude(),new Double(0d));
    }
}
