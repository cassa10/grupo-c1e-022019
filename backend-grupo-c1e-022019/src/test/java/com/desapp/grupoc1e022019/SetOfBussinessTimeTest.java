package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBussinessTime;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.util.HashSet;

public class SetOfBussinessTimeTest {

    @Test
    public void testGivenAnSetOfBussinessTimeWhenAddABussinessTimeIntoTheSetWhichContainsThatObjectThenNothingHappen(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BussinessTime bussinessTime2 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        SetOfBussinessTime setOfBussinessTime = new SetOfBussinessTime();
        setOfBussinessTime.add(bussinessTime1);

        setOfBussinessTime.add(bussinessTime2);
        Assert.assertEquals(setOfBussinessTime.getBussinessTimes().size(),1);
        Assert.assertTrue(setOfBussinessTime.getBussinessTimes().contains(bussinessTime1));
    }
}
