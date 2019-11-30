package com.desapp.grupoc1e022019.model;


import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.location.Coord;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.PenalizedProvider;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.BusinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBusinessTime;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
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
        Coord coord1 = new Coord("-34.72418","-58.25265");
        Address address1 = new Address(coord1,"Quilmes, Buenos Aires");

        Coord coord2 = new Coord("-41.14557", "-71.30822");
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
    public void testGivenProvider1WithDeliveryMaxDistanceInKM5Point5AndProvider2WithDeliveryMaxDistanceInKM3Point5WhenTheyGetDeliveryMaxDistanceInKMThenTheyReturnTheirValues(){
        Provider provider1 = ProviderBuilder.aProvider().withDeliveryMaxDistanceInKM(5.5).build();
        Provider provider2 = ProviderBuilder.aProvider().withDeliveryMaxDistanceInKM(3.5).build();

        Assert.assertEquals(provider1.getDeliveryMaxDistanceInKM(),new Double(5.5));
        Assert.assertEquals(provider2.getDeliveryMaxDistanceInKM(),new Double(3.5));
    }

    @Test
    public void testGivenAProviderWithScheduleMondayAndTuesdayWithHour8To12WhenProviderRecievesGetScheduleThenItReturnsASetOfThesesBussinessHoursAndDay(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BusinessTime bussinessHourTuesday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        SetOfBusinessTime setBussinessHoursTuesday = new SetOfBusinessTime();
        setBussinessHoursTuesday.add(bussinessHourTuesday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.TUESDAY,setBussinessHoursTuesday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.TUESDAY,setBussinessHoursTuesday));
        Assert.assertEquals(provider.getScheduleDays().size(),2);
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),2);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12Wednesday16To20Friday8To20WhenProviderRecievesGetScheduleThenItReturnsASetOfThesesBussinessHoursAndDay(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BusinessTime bussinessHourWednesday = new BusinessTime(Time.valueOf("16:00:00"),Time.valueOf("20:00:00"));
        BusinessTime bussinessHourFriday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("20:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        SetOfBusinessTime setBussinessHoursWednesday = new SetOfBusinessTime();
        setBussinessHoursWednesday.add(bussinessHourWednesday);

        SetOfBusinessTime setBussinessHoursFriday = new SetOfBusinessTime();
        setBussinessHoursFriday.add(bussinessHourFriday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.WEDNESDAY,setBussinessHoursWednesday);
        mapOfBussinessDayAndHour.put(DayOfWeek.FRIDAY,setBussinessHoursFriday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.WEDNESDAY,setBussinessHoursWednesday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.FRIDAY,setBussinessHoursFriday));
        Assert.assertEquals(provider.getScheduleDays().size(),3);
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),3);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12Wednesday16To20Friday8To20WhenProviderRecievesDeleteBussinessTimeWEDNESDAYThenDayWednesdayAndTheirValueIsRemovedFromSchedule(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BusinessTime bussinessHourWednesday = new BusinessTime(Time.valueOf("16:00:00"),Time.valueOf("20:00:00"));
        BusinessTime bussinessHourFriday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("20:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        SetOfBusinessTime setBussinessHoursWednesday = new SetOfBusinessTime();
        setBussinessHoursWednesday.add(bussinessHourWednesday);

        SetOfBusinessTime setBussinessHoursFriday = new SetOfBusinessTime();
        setBussinessHoursFriday.add(bussinessHourFriday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.WEDNESDAY,setBussinessHoursWednesday);
        mapOfBussinessDayAndHour.put(DayOfWeek.FRIDAY,setBussinessHoursFriday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        provider.deleteBussinessTime(DayOfWeek.WEDNESDAY);

        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.FRIDAY));
        Assert.assertEquals(provider.getScheduleDays().size(),2);

        Assert.assertFalse(provider.getMapSchedule().containsKey(DayOfWeek.WEDNESDAY));

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.FRIDAY,setBussinessHoursFriday));
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),2);

        Assert.assertFalse(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.WEDNESDAY,setBussinessHoursWednesday));
    }

    @Test
    public void testGivenAProviderWithScheduleMondayAndTuesdayWithHour8To12WhenProviderRecievesDeleteBussinessTimeFRIDAYThenItIsNotAffected(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));
        BusinessTime bussinessHourTuesday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        SetOfBusinessTime setBussinessHoursTuesday = new SetOfBusinessTime();
        setBussinessHoursTuesday.add(bussinessHourTuesday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);
        mapOfBussinessDayAndHour.put(DayOfWeek.TUESDAY,setBussinessHoursTuesday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        provider.deleteBussinessTime(DayOfWeek.FRIDAY);

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.TUESDAY,setBussinessHoursTuesday));
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),2);
        Assert.assertEquals(provider.getScheduleDays().size(),2);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12WhenItRecievesAddBussinessTimeSUNDAYAndSetBussinessHoursSundayThenProviderScheduleAddTheNewBussinessHour(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        BusinessTime bussinessHourSunday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        provider.addBussinessTime(DayOfWeek.SUNDAY,bussinessHourSunday);

        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.SUNDAY));
        Assert.assertEquals(provider.getScheduleDays().size(),2);

        SetOfBusinessTime setBussinessHoursSunday = new SetOfBusinessTime();
        setBussinessHoursSunday.add(bussinessHourSunday);

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.SUNDAY,setBussinessHoursSunday));
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),2);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12WhenItRecievesAddBussinessTimeMONDAY17To20ThenProviderScheduleAddTheNewBussinessHour(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        BusinessTime newBussinessHour = new BusinessTime(Time.valueOf("17:00:00"),Time.valueOf("20:00:00"));

        provider.addBussinessTime(DayOfWeek.MONDAY,newBussinessHour);

        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertEquals(provider.getScheduleDays().size(),1);

        setBussinessHoursMonday.add(newBussinessHour);
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertEquals(provider.getBussinessHoursOf(DayOfWeek.MONDAY).size(),2);
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),1);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12WhenItRecievesSetBussinessTimeMONDAY17To20ThenProviderScheduleSetTheNewBussinessHourDeletingThePrevious(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        BusinessTime newBussinessHour = new BusinessTime(Time.valueOf("17:00:00"),Time.valueOf("20:00:00"));
        SetOfBusinessTime newSetBussinessHoursMonday = new SetOfBusinessTime();
        newSetBussinessHoursMonday.add(newBussinessHour);

        provider.setBussinessTime(DayOfWeek.MONDAY,newSetBussinessHoursMonday);

        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertEquals(provider.getScheduleDays().size(),1);

        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,newSetBussinessHoursMonday));
        Assert.assertEquals(provider.getBussinessHoursOf(DayOfWeek.MONDAY).size(),1);
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),1);
    }

    @Test
    public void testGivenAProviderWithScheduleMonday8To12WhenItRecievesSetBussinessTimeTUESDAY17To20ThenProviderScheduleSetTheNewBussinessHour(){
        BusinessTime bussinessHourMonday = new BusinessTime(Time.valueOf("8:00:00"),Time.valueOf("12:00:00"));

        SetOfBusinessTime setBussinessHoursMonday = new SetOfBusinessTime();
        setBussinessHoursMonday.add(bussinessHourMonday);

        Map<DayOfWeek, SetOfBusinessTime> mapOfBussinessDayAndHour = new HashMap<>();
        mapOfBussinessDayAndHour.put(DayOfWeek.MONDAY,setBussinessHoursMonday);

        Schedule schedule1 = new Schedule(mapOfBussinessDayAndHour);

        Provider provider = ProviderBuilder.aProvider().withSchedule(schedule1).build();

        BusinessTime newBussinessHourTuesday = new BusinessTime(Time.valueOf("17:00:00"),Time.valueOf("20:00:00"));
        SetOfBusinessTime setNewBussinessHourTuesday = new SetOfBusinessTime();
        setNewBussinessHourTuesday.add(newBussinessHourTuesday);

        provider.setBussinessTime(DayOfWeek.TUESDAY,setNewBussinessHourTuesday);

        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.MONDAY));
        Assert.assertTrue(provider.getMapSchedule().containsKey(DayOfWeek.TUESDAY));
        Assert.assertEquals(provider.getScheduleDays().size(),2);


        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.MONDAY,setBussinessHoursMonday));
        Assert.assertTrue(provider.getSchedule().containsDayAndBussinessTime(DayOfWeek.TUESDAY,setNewBussinessHourTuesday));
        Assert.assertEquals(provider.getScheduleBussinessTimes().size(),2);
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

    @Test
    public void testWhenProviderDeletesAMenuThenItHasOneLessMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu dummyMenu = MenuBuilder.aMenu().build();
        provider.addMenu(dummyMenu);

        provider.deleteMenu(dummyMenu);

        Assert.assertTrue(provider.getMenus().isEmpty());
    }

    @Test
    public void testWhenProviderGetsHisCreditsThenReturnACreditWithZeroAmount(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertEquals(provider.getCredit() ,new Credit(0d));
    }

    @Test
    public void testGivenAProviderWithZeroCreditAmountWhenProviderRecievesCredit10AmountThenItHas10CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().build();
        provider.depositCredit(new Credit(10d));

        Assert.assertEquals(provider.getCredit(),new Credit(10d));
    }

    @Test
    public void testGivenAProviderWith10CreditAmountWhenProviderRecievesCredit10AmountThenItHas20CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10d)).build();
        provider.depositCredit(new Credit(10d));

        Assert.assertEquals(provider.getCredit(),new Credit(20d));
    }

    @Test
    public void testGivenAProviderWith10Point4CreditAmountWhenProviderRecievesCredit10Point44AmountThenItHas20Point84CreditAmount(){
        Provider provider = ProviderBuilder.aProvider().withCredit(new Credit(10.4d)).build();
        provider.depositCredit(new Credit(10.44d));

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

    @Test
    public void testGivenAProviderByDefaultIsNormalWhenItRecievesIsNormalProviderReturnsTrueAndIsPenalizedReturnsFalseAndGetProviderStateNameReturnsNORMAL(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertTrue(provider.isNormalProvider());
        Assert.assertFalse(provider.isPenalized());

        Assert.assertEquals(provider.getProviderStateName(),"NormalProvider");
    }

    @Test
    public void testGivenAProviderPenalizedWhenItRecievesIsNormalProviderReturnsFalseAndIsPenalizedReturnsTrueAndGetProviderStateNameReturnsPENALIZED(){
        Provider provider = ProviderBuilder.aProvider().withProviderState(new PenalizedProvider()).build();

        Assert.assertFalse(provider.isNormalProvider());
        Assert.assertTrue(provider.isPenalized());

        Assert.assertEquals(provider.getProviderStateName(),"PenalizedProvider");
    }

    @Test
    public void testGivenAProviderByDefaultNormalWithZeroStrikesMenuWhenReceivesAddAStrikeTwoTimesThenProviderHasTwoStrikesMenu(){
        Provider provider = ProviderBuilder.aProvider().build();

        provider.addAStrike();
        provider.addAStrike();
        Assert.assertEquals(provider.getStrikesMenu(),new Integer(2));
    }

    @Test
    public void testGivenAProviderByDefaultNormalWith9StrikesMenuWhenReceivesAddAStrikeThenProviderHasTenStrikesMenuAndIsPenalized(){
        Provider provider = ProviderBuilder.aProvider().withStrikesMenu(9).build();

        provider.addAStrike();
        Assert.assertEquals(provider.getStrikesMenu(),new Integer(10));
        Assert.assertTrue(provider.isPenalized());
    }

    @Test
    public void testGivenAProviderByDefaultNormalWith10StrikesMenuWhenReceivesAddAStrikeThenProviderHasTenStrikesMenuAndIsPenalized(){
        Provider provider = ProviderBuilder.aProvider().withStrikesMenu(10).build();

        provider.addAStrike();
        Assert.assertEquals(provider.getStrikesMenu(),new Integer(11));
        Assert.assertTrue(provider.isPenalized());
    }


    @Test
    public void testGivenAProviderPenalizedWith10StrikesMenuWhenReceivesAddAStrikeThenProviderHasTenStrikesMenu(){
        Provider provider = ProviderBuilder.aProvider().withProviderState(new PenalizedProvider()).withStrikesMenu(10).build();

        provider.addAStrike();
        Assert.assertEquals(provider.getStrikesMenu(),new Integer(10));
        Assert.assertTrue(provider.isPenalized());
    }

    @Test
    public void testGivenAProviderPenalizedWhenReceivesAddAStrikeThenProviderDoesNotDoAnythingAndHasZeroStrikesMenu(){
        Provider provider = ProviderBuilder.aProvider().withProviderState(new PenalizedProvider()).build();

        provider.addAStrike();
        Assert.assertEquals(provider.getStrikesMenu(),new Integer(0));
        Assert.assertTrue(provider.isPenalized());
    }

    private ArrayList<Menu> getTwentyRandomMenus() {
        ArrayList<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            menus.add(MenuBuilder.aMenu().build());
        }
        return menus;
    }
}
