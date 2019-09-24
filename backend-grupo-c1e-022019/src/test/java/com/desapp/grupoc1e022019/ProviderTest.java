package com.desapp.grupoc1e022019;


import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.exception.RepeatedIDException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ProviderTest {

    @Test
    public void testGivenProvider1WithNamePepeAndProvider2WithNameLaloWhenTheyReceiveGetNameThenTheyReturnTheirNames(){
        Provider provider1 = ProviderBuilder.aProvider().withName("Pepe").build();
        Provider provider2 = ProviderBuilder.aProvider().withName("Lalo").build();

        Assert.assertEquals(provider1.getName(),"Pepe");
        Assert.assertEquals(provider2.getName(),"Lalo");
    }

    @Test
    public void testGivenProvider1WithLogoImg1AndProvider2WithLogoImg2WhenTheyReceiveGetLogoThenTheyReturnTheirLogos(){
        Provider provider1 = ProviderBuilder.aProvider().withLogo("Img1").build();
        Provider provider2 = ProviderBuilder.aProvider().withLogo("Img2").build();

        Assert.assertEquals(provider1.getLogo(),"Img1");
        Assert.assertEquals(provider2.getLogo(),"Img2");
    }

    @Test
    public void testGivenProvider1WithCityQuilmesAndProvider2WithCityVarelaWhenTheyReceiveGetCityThenTheyReturnTheirCities(){
        Provider provider1 = ProviderBuilder.aProvider().withCity("Quilmes").build();
        Provider provider2 = ProviderBuilder.aProvider().withCity("Varela").build();

        Assert.assertEquals(provider1.getCity(),"Quilmes");
        Assert.assertEquals(provider2.getCity(),"Varela");
    }

    @Test
    public void testGivenProvider1WithAddress1AndProvider2WithAddress1WhenTheyReceiveGetAddressThenTheyReturnTheirAddress(){
        Coord coord1 = new Coord(-34.72418,-58.25265);
        Address address1 = new Address(coord1,"Quilmes, Buenos Aires");

        Coord coord2 = new Coord(-41.14557, -71.30822);
        Address address2 = new Address(coord2,"Bariloche, Rio Negro");

        Provider provider1 = ProviderBuilder.aProvider().withAddress(address1).build();
        Provider provider2 = ProviderBuilder.aProvider().withAddress(address2).build();

        Assert.assertEquals(provider1.getAddress(),address1);
        Assert.assertEquals(provider2.getAddress(),address2);
    }

    @Test
    public void testGivenProvider1WithDescriptionTheBestProviderAndProvider2WithoutDescriptionWhenTheyReceiveGetDescriptionThenTheyReturnTheyDescriptions(){
        Provider provider1 = ProviderBuilder.aProvider().withDescription("The best provider").build();
        Provider provider2 = ProviderBuilder.aProvider().withDescription("").build();

        Assert.assertEquals(provider1.getDescription(),"The best provider");
        Assert.assertEquals(provider2.getDescription(),"");
    }

    @Test
    public void testWhenICreateANewProviderThenItHasNoMenus(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertTrue(provider.getMenus().isEmpty());
    }

    @Test
    public void testWhenAProviderAddANewMenuThenItHasOneMoreMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu = MenuBuilder.aMenu().withId(0).build();

        provider.addMenu(newMenu);

        Assert.assertEquals(provider.getMenus().size(), 1);
        Assert.assertTrue(provider.getMenus().contains(newMenu));
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
