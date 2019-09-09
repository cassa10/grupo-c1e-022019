package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.location.Address;
import com.desapp.grupoc1e022019.model.location.Coord;
import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

    @Test
    public void testGivenAnAddressWithCoordLat0dLgt1dAndLocationQuilmesBuenosAiresWhenItRecievesGetLocationThenItReturnsQuilmesBuenosAires(){
        String location = "Quilmes, Buenos Aires";
        Coord coord = new Coord(0d,1d);
        Address address = new Address(coord,location);

        Assert.assertEquals(address.getLocation(),"Quilmes, Buenos Aires");
    }

    @Test
    public void testGivenAnAddressWithCoordLat0dLgt1dAndLocationVarelaBuenosAiresWhenItRecievesGetLocationThenItReturnsVarelaBuenosAires(){
        String location = "Varela, Buenos Aires";
        Coord coord = new Coord(0d,1d);
        Address address = new Address(coord,location);

        Assert.assertEquals(address.getLocation(),"Varela, Buenos Aires");
    }

    @Test
    public void testGivenAnAddressWithCoordLat0dLgt1dWhenItRecievesGetCoordThenItReturnsCoordLat0dLgt1d(){
        String location = "Somewhere";
        Coord coord = new Coord(0d,1d);
        Address address = new Address(coord,location);

        Assert.assertEquals(address.getCoord(),coord);
    }

    @Test
    public void testGivenAnAddressWithCoordLat2dLgt0dWhenItRecievesGetCoordThenItReturnsCoordLat2dLgt0d(){
        String location = "Somewhere";
        Coord coord = new Coord(2d,0d);
        Address address = new Address(coord,location);

        Assert.assertEquals(address.getCoord(),coord);
    }

}
