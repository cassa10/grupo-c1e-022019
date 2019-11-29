package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import org.junit.Assert;
import org.junit.Test;

public class MenuPriceCalculatorTest {

    @Test
    public void testGivenTwoMenuPriceCalculatorWithTheirAtributesWhenGetTheirAttributesThenThesesAreReturned(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        MenuPriceCalculator aMenuPriceCalculator2 = new MenuPriceCalculator(100d,10,
                70d,15,60d);

        Assert.assertEquals(aMenuPriceCalculator.getPrice(),new Double(10));
        Assert.assertEquals(aMenuPriceCalculator.getFirstMinAmount(),new Integer(4));
        Assert.assertEquals(aMenuPriceCalculator.getFirstMinAmountPrice(),new Double(7));
        Assert.assertEquals(aMenuPriceCalculator.getSecondMinAmount(),new Integer(10));
        Assert.assertEquals(aMenuPriceCalculator.getSecondMinAmountPrice(),new Double(5d));

        Assert.assertEquals(aMenuPriceCalculator2.getPrice(),new Double(100));
        Assert.assertEquals(aMenuPriceCalculator2.getFirstMinAmount(),new Integer(10));
        Assert.assertEquals(aMenuPriceCalculator2.getFirstMinAmountPrice(),new Double(70));
        Assert.assertEquals(aMenuPriceCalculator2.getSecondMinAmount(),new Integer(15));
        Assert.assertEquals(aMenuPriceCalculator2.getSecondMinAmountPrice(),new Double(60d));
    }

    @Test
    public void testGivenAMenuPriceCalculatorWithPrice10_FstMinAmount4_FstMinPrice7_SndMinAmount10_SndMinPrice_5dWhenItCalculatesMenuPriceWithAmount3Returns30AsDouble(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        Assert.assertEquals(aMenuPriceCalculator.calculateCurrentMenuPrice(3),new Double(30));
    }

    @Test
    public void testGivenAMenuPriceCalculatorWithPrice10_FstMinAmount4_FstMinPrice7_SndMinAmount10_SndMinPrice_5dWhenItCalculatesMenuPriceWithAmount4Returns28AsDouble(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        Assert.assertEquals(aMenuPriceCalculator.calculateCurrentMenuPrice(4),new Double(28));
    }

    @Test
    public void testGivenAMenuPriceCalculatorWithPrice10_FstMinAmount4_FstMinPrice7_SndMinAmount10_SndMinPrice_5dWhenItCalculatesMenuPriceWithAmount9Returns63AsDouble(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        Assert.assertEquals(aMenuPriceCalculator.calculateCurrentMenuPrice(9),new Double(63));
    }

    @Test
    public void testGivenAMenuPriceCalculatorWithPrice10_FstMinAmount4_FstMinPrice7_SndMinAmount10_SndMinPrice_5dWhenItCalculatesMenuPriceWithAmount10Returns50AsDouble(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        Assert.assertEquals(aMenuPriceCalculator.calculateCurrentMenuPrice(10),new Double(50));
    }

    @Test
    public void testGivenAMenuPriceCalculatorWithPrice10_FstMinAmount4_FstMinPrice7_SndMinAmount10_SndMinPrice_5dWhenItCalculatesMenuPriceWithAmount55Returns275AsDouble(){
        MenuPriceCalculator aMenuPriceCalculator = new MenuPriceCalculator(10d,4,
                7d,10,5d);

        Assert.assertEquals(aMenuPriceCalculator.calculateCurrentMenuPrice(55),new Double(275));
    }
}
