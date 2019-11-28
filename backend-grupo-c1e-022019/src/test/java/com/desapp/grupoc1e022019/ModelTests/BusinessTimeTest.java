package com.desapp.grupoc1e022019.ModelTests;

import com.desapp.grupoc1e022019.model.providerComponents.schedule.BusinessTime;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;

public class BusinessTimeTest {

    @Test
    public void testGivenBussinessTime1From8AndTo16AndBussinessTime2From8AndTo20WhenGetFromAndToTimesThenTheyReturnTheirTimes(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BusinessTime businessTime2 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("20:00:00"));

        Assert.assertEquals(businessTime1.getTimeOpen(),Time.valueOf("8:00:00"));
        Assert.assertEquals(businessTime1.getTimeClose(),Time.valueOf("16:00:00"));

        Assert.assertEquals(businessTime2.getTimeOpen(),Time.valueOf("8:00:00"));
        Assert.assertEquals(businessTime2.getTimeClose(),Time.valueOf("20:00:00"));
    }

    @Test
    public void testTwoBussinessTimesWithSameOpenTimeAndCloseTimeValuesAreEqual(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BusinessTime businessTime2 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertEquals(businessTime1, businessTime2);
    }

    @Test
    public void testTwoBussinessTimesWithDifferentOpenTimeValueAreNotEqual(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BusinessTime businessTime2 = new BusinessTime(Time.valueOf("9:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(businessTime1, businessTime2);
    }

    @Test
    public void testTwoBussinessTimesWithDifferentCloseTimeValueAreNotEqual(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BusinessTime businessTime2 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("18:00:00"));

        Assert.assertNotEquals(businessTime1, businessTime2);
    }

    @Test
    public void testNoObjectExceptTheSameBussinessTimeCannotBeEqualToAnyBussinessTime(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(businessTime1,new Object());
        Assert.assertNotEquals(businessTime1,"xD");
    }

    @Test
    public void testNullIsNotEqualsWithAnyBussinessTime(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        Assert.assertNotEquals(businessTime1,null);
    }
}
