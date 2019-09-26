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

        Assert.assertEquals(bussinessTime1.getTimeFrom(),Time.valueOf("8:00:00"));
        Assert.assertEquals(bussinessTime1.getTimeTo(),Time.valueOf("16:00:00"));

        Assert.assertEquals(bussinessTime2.getTimeFrom(),Time.valueOf("8:00:00"));
        Assert.assertEquals(bussinessTime2.getTimeTo(),Time.valueOf("20:00:00"));
    }
}
