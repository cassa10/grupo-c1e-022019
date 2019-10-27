package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;

public class BussinessTimeTest {

    @Test
    public void testGivenBussinessTime1From8AndTo16AndBussinessTime2From8AndTo20WhenGetFromAndToTimesThenTheyReturnTheirTimes(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BussinessTime bussinessTime2 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("20:00:00"));

        Assert.assertEquals(bussinessTime1.getTimeOpen(),Time.valueOf("8:00:00"));
        Assert.assertEquals(bussinessTime1.getTimeClose(),Time.valueOf("16:00:00"));

        Assert.assertEquals(bussinessTime2.getTimeOpen(),Time.valueOf("8:00:00"));
        Assert.assertEquals(bussinessTime2.getTimeClose(),Time.valueOf("20:00:00"));
    }

    @Test
    public void testTwoBussinessTimesWithSameOpenTimeAndCloseTimeValuesAreEqual(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BussinessTime bussinessTime2 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertEquals(bussinessTime1,bussinessTime2);
    }

    @Test
    public void testTwoBussinessTimesWithDifferentOpenTimeValueAreNotEqual(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BussinessTime bussinessTime2 = new BussinessTime(Time.valueOf("9:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(bussinessTime1,bussinessTime2);
    }

    @Test
    public void testTwoBussinessTimesWithDifferentCloseTimeValueAreNotEqual(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BussinessTime bussinessTime2 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("18:00:00"));

        Assert.assertNotEquals(bussinessTime1,bussinessTime2);
    }

    @Test
    public void testNoObjectExceptTheSameBussinessTimeCannotBeEqualToAnyBussinessTime(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(bussinessTime1,new Object());
        Assert.assertNotEquals(bussinessTime1,"xD");
    }

    @Test
    public void testNullIsNotEqualsWithAnyBussinessTime(){
        BussinessTime bussinessTime1 = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(bussinessTime1,null);
    }
}
