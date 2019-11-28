package com.desapp.grupoc1e022019.ModelTests;

import com.desapp.grupoc1e022019.model.Credit;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class CreditTest extends TestCase {
    @Test
    public void testWhenICreateANewCreditWithAmount10dThenItsAmountIs10(){
        Credit credit = new Credit(10d);
        Assert.assertEquals(credit.getAmount(),new Double(10));
    }
    @Test
    public void testWhenISumACreditWithAmount10dWithAnotherCreditWithAmount5ThenTheResultIsANewCreditWithAmount15(){
        Credit credit10 = new Credit(10d);
        Credit credit5 = new Credit(5d);
        Assert.assertEquals(credit10.sum(credit5),new Credit(15d));
    }

    @Test
    public void testTwoCreditsWithTheSameAmountAreEquals(){
        Credit credit5 = new Credit(5d);

        Assert.assertEquals(credit5,new Credit(5d));
    }
}
