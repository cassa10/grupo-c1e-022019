package com.desapp.grupoc1e022019.ModelTests;

import com.desapp.grupoc1e022019.model.providerComponents.schedule.BusinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBusinessTime;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;

public class SetOfBusinessTimeTest {

    @Test
    public void testGivenAnSetOfBussinessTimeWhenAddABussinessTimeIntoTheSetWhichContainsThatObjectThenNothingHappen(){
        BusinessTime businessTime1 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));
        BusinessTime businessTime2 = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("16:00:00"));

        SetOfBusinessTime setOfBusinessTime = new SetOfBusinessTime();
        setOfBusinessTime.add(businessTime1);

        setOfBusinessTime.add(businessTime2);
        Assert.assertEquals(setOfBusinessTime.getBusinessTimes().size(),1);
        Assert.assertTrue(setOfBusinessTime.getBusinessTimes().contains(businessTime1));
    }
}
