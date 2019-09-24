package com.desapp.grupoc1e022019;


import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.exception.RepeatedIDException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ProviderTest {
    @Test
    public void testWhenICreateANewProviderThenItHasNoMenus(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertEquals(provider.getMenus(), new ArrayList<>());
    }
    @Test
    public void testWhenAProviderAddANewMenuThenItHasOneMoreMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu = MenuBuilder.aMenu().withId(0).build();

        provider.addMenu(newMenu);

        Assert.assertEquals(provider.getMenus().size(), 1);
    }
    @Test(expected = MaximumMenusSizeException.class)
    public void testWhenAProviderAddANewMenuHaving20ItCannotAddMoreThenItRaiseMaxCantException()  {
        ArrayList<Menu>  twentyMenus = getTwentyRandomMenus();
        Provider provider = ProviderBuilder.aProvider().withMenus(twentyMenus).build();
        Menu newMenu = MenuBuilder.aMenu().build();

        provider.addMenu(newMenu);

    }

    @Test(expected = RepeatedIDException.class)
    public void testWhenAProviderAddAMenuButTheIdAlreadyExistsThenItRaiseRepeatedIDException()  {
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu1 = MenuBuilder.aMenu().withId(1).build();
        Menu newMenu2 = MenuBuilder.aMenu().withId(1).build();
        provider.addMenu(newMenu1);

        provider.addMenu(newMenu2);

    }

    @Test
    public void testWhenProviderDeletesAMenuThenItHasOneLessMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu dummyMenu = MenuBuilder.aMenu().build();
        provider.addMenu(dummyMenu);

        provider.deleteMenu(dummyMenu);

        Assert.assertTrue(provider.getMenus().isEmpty());
    }

    @Test
    public void testWhenProviderUpdatesAMenuThenItHasTheSameNumberOfMenus(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu oldMenu = MenuBuilder.aMenu().withId(1).build();
        Menu dummyMenu = MenuBuilder.aMenu().withId(99).build();
        Menu updatedMenu = MenuBuilder.aMenu().withId(1).build();
        provider.addMenu(dummyMenu);
        provider.addMenu(oldMenu);

        provider.updateMenu(1,updatedMenu);

        Assert.assertTrue(provider.getMenus().contains(updatedMenu));
    }

    @Test
    public void testWhenProviderGetsHisCreditsThenReturnACreditWithZeroAmount(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertEquals(provider.getCredit() ,new Credit(0d));
    }

    @Test
    public void testGivenAProviderWithZeroCreditAmountWhenProviderRecievesCredit10AmountThenItHas10CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().build();
        provider.recievesCredit(new Credit(10d));

        Assert.assertEquals(provider.getCredit(),new Credit(10d));
    }

    @Test
    public void testGivenAProviderWith10CreditAmountWhenProviderRecievesCredit10AmountThenItHas20CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10d)).build();
        provider.recievesCredit(new Credit(10d));

        Assert.assertEquals(provider.getCredit(),new Credit(20d));
    }

    @Test
    public void testGivenAProviderWith10Point4CreditAmountWhenProviderRecievesCredit10Point44AmountThenItHas20Point84CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10.4d)).build();
        provider.recievesCredit(new Credit(10.44d));

        Assert.assertEquals(provider.getCredit(),new Credit(20.84d));
    }

    @Test(expected = InsufficientCreditException.class)
    public void testGivenAProviderWithZeroCreditsWhenItWantToWithdraw10CreditsThenAnInsufficientCreditExceptionArrays(){
        Provider provider = ProviderBuilder.aProvider().build();
        provider.withdrawCredit(new Credit(10d));
    }

    @Test
    public void testGivenAProviderWithTenCreditsWhenItWantToWithdraw10CreditsThenTheWithdrawnCreditCreditsAreReturnedAndTheirCreditAmountIsZero(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10d)).build();
        Credit withdrawn = provider.withdrawCredit(new Credit(10d));

        Assert.assertEquals(withdrawn,new Credit(10d));

        Assert.assertEquals(provider.getCredit(),new Credit(0d));
    }

    @Test
    public void testGivenAProviderWithTenCreditsWhenItWantToWithdraw2CreditsThenTheWithdrawnCreditsAreReturnesAndTheirCreditAmountIs8(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10d)).build();
        Credit withdrawn = provider.withdrawCredit(new Credit(2d));

        Assert.assertEquals(withdrawn,new Credit(2d));

        Assert.assertEquals(provider.getCredit(),new Credit(8d));
    }

    private ArrayList<Menu> getTwentyRandomMenus() {
        ArrayList<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            menus.add(MenuBuilder.aMenu().build());
        }
        return menus;
    }
}
