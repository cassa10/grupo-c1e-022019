package com.desapp.grupoc1e022019;


import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.exception.RepeatedIDException;
import com.desapp.grupoc1e022019.model.Credit;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.*;

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
    public void testGivenProvider1WithDescriptionTheBestProviderAndProvider2WithoutDescriptionWhenTheyReceiveGetDescriptionThenTheyReturnTheirDescriptions(){
        Provider provider1 = ProviderBuilder.aProvider().withDescription("The best provider").build();
        Provider provider2 = ProviderBuilder.aProvider().withDescription("").build();

        Assert.assertEquals(provider1.getDescription(),"The best provider");
        Assert.assertEquals(provider2.getDescription(),"");
    }

    @Test
    public void testGivenProvider1WithWeburlPepeDotComAndProvider2WithoutWebUrlWhenTheyReceiveGetWebURLThenTheyReturnTheirWebUrls(){
        Provider provider1 = ProviderBuilder.aProvider().withWebUrl("pepe.com").build();
        Provider provider2 = ProviderBuilder.aProvider().withWebUrl("").build();

        Assert.assertEquals(provider1.getWebURL(),"pepe.com");
        Assert.assertEquals(provider2.getWebURL(),"");
    }

    @Test
    public void testGivenProvider1WithEmailPepeAtGmailDotComAndProvider2WithEmailLaloAtHotmailDotComWhenTheyReceiveGetEmailThenTheyReturnTheirEmails(){
        Provider provider1 = ProviderBuilder.aProvider().withEmail("pepe@gmail.com").build();
        Provider provider2 = ProviderBuilder.aProvider().withEmail("lalo@hotmail.com").build();

        Assert.assertEquals(provider1.getEmail(),"pepe@gmail.com");
        Assert.assertEquals(provider2.getEmail(),"lalo@hotmail.com");
    }

    @Test
    public void testGivenProvider1WithTelNumber12344321AndProvider2WithTelNumber43211234WhenTheyReceiveGetEmailThenTheyReturnTheirTelNumbers(){
        Provider provider1 = ProviderBuilder.aProvider().withTelNumber("12344321").build();
        Provider provider2 = ProviderBuilder.aProvider().withTelNumber("43211234").build();

        Assert.assertEquals(provider1.getTelNumber(),"12344321");
        Assert.assertEquals(provider2.getTelNumber(),"43211234");
    }

    @Test
    public void testGivenAProviderWithScheduleMondayAndTuesdayWithHour8To12WhenProviderRecievesGetScheduleThenItReturnsASetOfThesesBussinessHoursAndDay(){
        BussinessTime bussinessHourMonday = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BussinessTime bussinessHourTuesday = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        Map<DayOfWeek,BussinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,bussinessHourMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.TUESDAY,bussinessHourTuesday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        Assert.assertTrue(provider.getSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertTrue(provider.getSchedule().containsKey(DayOfWeek.TUESDAY));
        Assert.assertEquals(provider.getSchedule().keySet().size(),2);

        Assert.assertTrue(provider.getSchedule().containsValue(bussinessHourMonday));
        Assert.assertTrue(provider.getSchedule().containsValue(bussinessHourTuesday));
        Assert.assertEquals(provider.getSchedule().values().size(),2);
    }
    @Test
    public void testGivenAProviderWithScheduleMonday8To12Wednesday16To20Friday8To20WhenProviderRecievesGetScheduleThenItReturnsASetOfThesesBussinessHoursAndDay(){
        BussinessTime bussinessHourMonday = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BussinessTime bussinessHourWednesday = new BussinessTime(Time.valueOf("16:00:00"),Time.valueOf("20:00:00"));
        BussinessTime bussinessHourFriday = new BussinessTime(Time.valueOf("8:00:00"),Time.valueOf("20:00:00"));

        Map<DayOfWeek,BussinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,bussinessHourMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.WEDNESDAY,bussinessHourWednesday);
        mapOfBussinessDayAndHour.put(DayOfWeek.FRIDAY,bussinessHourFriday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        Assert.assertTrue(provider.getSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertTrue(provider.getSchedule().containsKey(DayOfWeek.WEDNESDAY));
        Assert.assertTrue(provider.getSchedule().containsKey(DayOfWeek.FRIDAY));
        Assert.assertEquals(provider.getSchedule().keySet().size(),3);

        Assert.assertTrue(provider.getSchedule().containsValue(bussinessHourMonday));
        Assert.assertTrue(provider.getSchedule().containsValue(bussinessHourWednesday));
        Assert.assertTrue(provider.getSchedule().containsValue(bussinessHourFriday));
        Assert.assertEquals(provider.getSchedule().values().size(),3);
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
