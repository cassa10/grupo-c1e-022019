package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.Provider;
import com.desapp.grupoc1e022019.model.menuComponents.CategoryMenu;
import com.desapp.grupoc1e022019.model.menuComponents.EffectiveDate;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.menuComponents.menuState.CancelledMenu;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.builder.ProviderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuTest {

    @Test
    public void testGivenTwoMenusWithDifferentIdWhenTheyRecieveGetIdThenTheyReturnTheirIds(){
        Menu menu1 = aMenu().withId(1).build();
        Menu menu2 = aMenu().withId(2).build();

        Assert.assertEquals(menu1.idAsInt(),1);
        Assert.assertEquals(menu2.idAsInt(),2);
    }

    @Test
    public void testGivenTwoMenusWithDifferentProviderWhenTheyRecieveGetProviderThenTheyReturnTheirProviders(){
        Provider provider1 = aProvider("Pepe");
        Provider provider2 = aProvider("Cacho");

        Menu menu1 = aMenu().withProvider(provider1).build();
        Menu menu2 = aMenu().withProvider(provider2).build();

        Assert.assertEquals(menu1.getProvider(),provider1);
        Assert.assertEquals(menu2.getProvider(),provider2);
    }

    @Test
    public void testGivenAMenu1WithNamePepeAndAMenu2WithNamePromoWhenTheyRecieveGetNameThenTheyReturnTheirNames(){
        Menu menu1 = aMenu().withName("Pepe").build();
        Menu menu2 = aMenu().withName("Promo").build();

        Assert.assertEquals(menu1.getName(),"Pepe");
        Assert.assertEquals(menu2.getName(),"Promo");
    }

    @Test
    public void testGivenAMenu1WithDescDeliciousAndAMenu2WithDescBigPromoWhenTheyRecieveGetDescriptionThenTheyReturnTheirDescriptions(){
        Menu menu1 = aMenu().withDescription("Delicious").build();
        Menu menu2 = aMenu().withDescription("Big Promo").build();

        Assert.assertEquals(menu1.getDescription(),"Delicious");
        Assert.assertEquals(menu2.getDescription(),"Big Promo");
    }

    @Test
    public void testGivenAMenu1WithoutCategoryAMenu2WithCategoryHamburgerAndAMenu3WithCategoriesPizzaGreenVeganWhenTheyRecieveGetCategoriesThenTheyReturnTheirListOfCategories(){
        List<CategoryMenu> categoriesMenu1 = new ArrayList<>();

        List<CategoryMenu> categoriesMenu2 = new ArrayList<>();
        categoriesMenu2.add(CategoryMenu.HAMBURGER);

        List<CategoryMenu> categoriesMenu3 = new ArrayList<>();
        categoriesMenu3.add(CategoryMenu.PIZZA);
        categoriesMenu3.add(CategoryMenu.GREEN);
        categoriesMenu3.add(CategoryMenu.VEGAN);

        Menu menu1 = aMenu().withCategories(categoriesMenu1).build();
        Menu menu2 = aMenu().withCategories(categoriesMenu2).build();
        Menu menu3 = aMenu().withCategories(categoriesMenu3).build();

        Assert.assertTrue(menu1.getCategories().isEmpty());

        Assert.assertTrue(menu2.getCategories().containsAll(categoriesMenu2));
        Assert.assertEquals(menu2.getCategories().size(),1);

        Assert.assertTrue(menu3.getCategories().containsAll(categoriesMenu3));
        Assert.assertEquals(menu3.getCategories().size(),3);
    }

    @Test
    public void testGivenAMenu1WithDeliveryValue0AsDoubleAndAMenu2WithDeliveryValue5AsDoubleWhenTheyRecieveGetDeliveryValueThenTheyReturnTheirDeliveryValueAsDouble(){
        Menu menu1 = aMenu().withDeliveryValue(0d).build();
        Menu menu2 = aMenu().withDeliveryValue(5d).build();

        Assert.assertEquals(menu1.getDeliveryValue(),new Double(0));
        Assert.assertEquals(menu2.getDeliveryValue(),new Double(5));
    }

    @Test
    public void testGivenAMenu1WithEffectiveDate_DateAAsValidFromAndDateBAsGoodThru_AndAMenu2WithEffectiveDate_DateXAsValidFromAndDateYAsGoodThru_WhenTheyRecieveGetEffectiveDateValidFromAndGoodThruThenTheyReturnTheirValues(){

        LocalDate dateAValidFrom = LocalDate.of(2018,1,1);
        LocalDate dateBGoodThru = LocalDate.of(2018,3,1);

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(dateAValidFrom,dateBGoodThru);

        LocalDate dateXValidFrom = LocalDate.of(2020,1,1);
        LocalDate dateYGoodThru = LocalDate.of(2020,12,31);

        EffectiveDate effectiveDateMenu2 = new EffectiveDate(dateXValidFrom,dateYGoodThru);

        Menu menu1 = aMenu().withEffectiveDate(effectiveDateMenu1).build();
        Menu menu2 = aMenu().withEffectiveDate(effectiveDateMenu2).build();

        Assert.assertEquals(menu1.getEffectiveDateValidFrom(),dateAValidFrom);
        Assert.assertEquals(menu1.getEffectiveDateGoodThru(),dateBGoodThru);

        Assert.assertEquals(menu2.getEffectiveDateValidFrom(),dateXValidFrom);
        Assert.assertEquals(menu2.getEffectiveDateGoodThru(),dateYGoodThru);
    }

    @Test
    public void testGivenAMenu1AverageDeliveryTimeInMinutes15AndAMenu2WithAverageDeliveryTimeInMinutes30WhenTheyRecieveGetAverageDeliveryTimeInMinutesThenTheyReturnTheirAverageDeliveryTimeInMuntesAsInteger(){
        Menu menu1 = aMenu().withAverageDeliveryTimeInMinutes(15).build();
        Menu menu2 = aMenu().withAverageDeliveryTimeInMinutes(30).build();

        Assert.assertEquals(menu1.getAverageDeliveryTimeInMinutes(),new Integer(15));
        Assert.assertEquals(menu2.getAverageDeliveryTimeInMinutes(),new Integer(30));
    }

    @Test
    public void testGivenAMenuWithMenuPriceCalculatorWhichPrice9FstMinAmount3FstPrice6SndMinAmount5SndPrice4WhenAMenuGetsEveryPreviousAttributesThenAMenuReturnsInEachMethodTheSameValue(){
        double price = 9;
        int fstMinAmount = 3;
        double fstPrice = 6;
        int sndMinAmount = 5;
        double sndPrice = 4;
        MenuPriceCalculator menuPriceCalculator = new MenuPriceCalculator(price,fstMinAmount,fstPrice,sndMinAmount,sndPrice);

        Menu aMenu = aMenu().withMenuPriceCalculator(menuPriceCalculator).build();

        Assert.assertEquals(aMenu.getPrice(),new Double(price));
        Assert.assertEquals(aMenu.getFirstMinAmountPrice(),new Double(fstPrice));
        Assert.assertEquals(aMenu.getSecondMinAmountPrice(),new Double(sndPrice));

        Assert.assertEquals(aMenu.getFirstMinAmount(),new Integer(fstMinAmount));
        Assert.assertEquals(aMenu.getSecondMinAmount(),new Integer(sndMinAmount));
    }

    @Test
    public void testGivenAMenuWithMenuPriceCalculatorWhichPrice80FstMinAmount15FstPrice75SndMinAmount25SndPrice60WhenAMenuGetsEveryPreviousAttributesThenAMenuReturnsInEachMethodTheSameValue(){
        double price = 80;
        int fstMinAmount = 15;
        double fstPrice = 75;
        int sndMinAmount = 25;
        double sndPrice = 60;
        MenuPriceCalculator menuPriceCalculator = new MenuPriceCalculator(price,fstMinAmount,fstPrice,sndMinAmount,sndPrice);

        Menu aMenu = aMenu().withMenuPriceCalculator(menuPriceCalculator).build();

        Assert.assertEquals(aMenu.getPrice(),new Double(price));
        Assert.assertEquals(aMenu.getFirstMinAmountPrice(),new Double(fstPrice));
        Assert.assertEquals(aMenu.getSecondMinAmountPrice(),new Double(sndPrice));

        Assert.assertEquals(aMenu.getFirstMinAmount(),new Integer(fstMinAmount));
        Assert.assertEquals(aMenu.getSecondMinAmount(),new Integer(sndMinAmount));
    }

    @Test
    public void testGivenAMenuWithAnyMenuPriceCalculatorWhenAMenuSetPrice80FstMinAmount15FstPrice75SndMinAmount25SndPrice60WhenAMenuGetsEveryPreviousAttributesThenAMenuReturnsInEachMethodTheSameValueAsSetted(){
        Menu aMenu = aMenu().withMenuPriceCalculator(anyMenuPriceCalculator()).build();

        aMenu.setPrice(100d);
        aMenu.setFirstMinAmountPrice(80d);
        aMenu.setSecondMinAmountPrice(60d);

        aMenu.setFirstMinAmount(6);
        aMenu.setSecondMinAmount(10);

        Assert.assertEquals(aMenu.getPrice(),new Double(100));
        Assert.assertEquals(aMenu.getFirstMinAmountPrice(),new Double(80));
        Assert.assertEquals(aMenu.getSecondMinAmountPrice(),new Double(60));

        Assert.assertEquals(aMenu.getFirstMinAmount(),new Integer(6));
        Assert.assertEquals(aMenu.getSecondMinAmount(),new Integer(10));
    }

    @Test
    public void testGivenAMenu1WithMaxSalesPerDay100AndAMenu2WithMaxSalesPerDay30WhenTheyRecieveGetMaxSalesPerDayThenTheyReturnTheirMaxSalesPerDayAsInteger(){
        Menu menu1 = aMenu().withMaxSalesPerDay(100).build();
        Menu menu2 = aMenu().withMaxSalesPerDay(30).build();

        Assert.assertEquals(menu1.getMaxSalesPerDay(),new Integer(100));
        Assert.assertEquals(menu2.getMaxSalesPerDay(),new Integer(30));
    }

    @Test
    public void testGivenAMenuByDefaultIsNormalWhenRecievesIsNormalReturnsTrueAndGetMenuStateNameReturnsNORMAL(){
        Menu menu1 = aMenu().build();

        Assert.assertEquals(menu1.getMenuStateName(),"NORMAL");
        Assert.assertTrue(menu1.isNormalState());
        Assert.assertFalse(menu1.isCancelledState());
    }

    @Test
    public void testGivenAMenuWithStateCancelledWhenRecievesIsCancelledReturnsTrueAndGetMenuStateNameReturnsCANCELLED(){
        Menu menu1 = aMenu().withMenuState(new CancelledMenu()).build();

        Assert.assertEquals(menu1.getMenuStateName(),"CANCELLED");
        Assert.assertTrue(menu1.isCancelledState());
        Assert.assertFalse(menu1.isNormalState());
    }

    @Test
    public void testGivenANormalMenu1AndANormalMenu2WhenFirstOneRecievesAddRate5AndSecondOneRecievesAddRates3ThenMenuRankAverageOfFirstMenuIs5AndSecondMenuIs3BothAsDouble(){
        Menu menu1 = aMenu().build();
        Menu menu2 = aMenu().build();

        menu1.addRate(5);
        menu2.addRate(3);

        Assert.assertEquals(menu1.getRankAverage(),new Double(5));
        Assert.assertEquals(menu2.getRankAverage(),new Double(3));
    }

    @Test
    public void testGivenANormalMenuWhenRecievesAddRate4ThreeTimesAndAddRate3ThenMenuRankAverageIs3Point75(){
        Menu aMenu = aMenu().build();

        addRateNTimes(aMenu,4,3);
        aMenu.addRate(3);

        Assert.assertEquals(aMenu.getRankAverage(),new Double(3.75));
    }

    @Test
    public void testGivenANormalMenuWhenRecievesAddRate2NineteenTimesAndAddRate1ThenMenuRankAverageIs1Point95AndMenuStateIsCancelledAndAddRateDoesNotWork(){
        Menu aMenu = aMenu().build();

        addRateNTimes(aMenu,2,19);
        aMenu.addRate(1);

        Assert.assertEquals(aMenu.getRankAverage(),new Double(1.95));
        Assert.assertTrue(aMenu.isCancelledState());
        Assert.assertFalse(aMenu.isNormalState());

        aMenu.addRate(5);
        Assert.assertEquals(aMenu.getRankAverage(),new Double(1.95));

        aMenu.addRate(1);
        Assert.assertEquals(aMenu.getRankAverage(),new Double(1.95));
    }

    @Test
    public void testGivenAMenuWithEffectiveDate_2018January1stAsValidFromAnd2018March1stAsGoodThruWhenAMenuRecievesTodayIsBeingAnEffectiveDateReturnsFalse(){

        LocalDate validFrom = LocalDate.of(2018,1,1);
        LocalDate goodThru = LocalDate.of(2018,3,1);

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(validFrom,goodThru);

        Menu aMenu = aMenu().withEffectiveDate(effectiveDateMenu1).build();

        Assert.assertFalse(aMenu.todayIsBeingAnEffectiveDate());
    }

    @Test
    public void testGivenAMenuWithEffectiveDate_2019January1stAsValidFromAnd99999January1stAsGoodThruWhenAMenuRecievesTodayIsBeingAnEffectiveDateReturnsTrue(){

        LocalDate validFrom = LocalDate.of(2018,1,1);
        LocalDate goodThru = LocalDate.of(99999,1,1);

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(validFrom,goodThru);

        Menu aMenu = aMenu().withEffectiveDate(effectiveDateMenu1).build();

        Assert.assertTrue(aMenu.todayIsBeingAnEffectiveDate());
    }

    @Test
    public void testGivenAMenuWithEffectiveDate_2019January1stAsValidFromAndTodayDateAsGoodThruWhenAMenuRecievesTodayIsBeingAnEffectiveDateReturnsTrue(){

        LocalDate validFrom = LocalDate.of(2018,1,1);
        LocalDate goodThru = LocalDate.now();

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(validFrom,goodThru);

        Menu aMenu = aMenu().withEffectiveDate(effectiveDateMenu1).build();

        Assert.assertTrue(aMenu.todayIsBeingAnEffectiveDate());
    }

    @Test
    public void testGivenAMenuWithEffectiveDate_TodayDateAsValidFromAnd99999January1stAsGoodThruWhenAMenuRecievesTodayIsBeingAnEffectiveDateReturnsTrue(){

        LocalDate validFrom = LocalDate.now();
        LocalDate goodThru = LocalDate.of(99999,1,1);

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(validFrom,goodThru);

        Menu aMenu = aMenu().withEffectiveDate(effectiveDateMenu1).build();

        Assert.assertTrue(aMenu.todayIsBeingAnEffectiveDate());
    }

    @Test
    public void testGivenAMenuWithEffectiveDate_TodayDateAsValidFromAndTodayDateAsGoodThruWhenAMenuRecievesTodayIsBeingAnEffectiveDateReturnsTrue(){

        LocalDate validFrom = LocalDate.now();
        LocalDate goodThru = LocalDate.now();

        EffectiveDate effectiveDateMenu1 = new EffectiveDate(validFrom,goodThru);

        Menu aMenu = aMenu().withEffectiveDate(effectiveDateMenu1).build();

        Assert.assertTrue(aMenu.todayIsBeingAnEffectiveDate());
    }

    @Test
    public void testGivenAMenuWithMenuPriceCalculatorWhichPrice9FstMinAmount3FstPrice6SndMinAmount6SndPrice5WhenAMenuRequirePriceWithAmount2Returns18_WithAmount3Returns18_WithAmount6Returns30(){
        double price = 9;
        int fstMinAmount = 3;
        double fstPrice = 6;
        int sndMinAmount = 6;
        double sndPrice = 5;
        MenuPriceCalculator menuPriceCalculator = new MenuPriceCalculator(price,fstMinAmount,fstPrice,sndMinAmount,sndPrice);

        Menu aMenu = aMenu().withMenuPriceCalculator(menuPriceCalculator).build();

        Assert.assertEquals(aMenu.priceWithAmount(2),new Double(18));
        Assert.assertEquals(aMenu.priceWithAmount(3),new Double(18));
        Assert.assertEquals(aMenu.priceWithAmount(6),new Double(30));
    }

    private MenuBuilder aMenu(){
        return MenuBuilder.aMenu();
    }

    private Provider aProvider(String name){
        return ProviderBuilder.aProvider().withName(name).build();
    }

    private MenuPriceCalculator anyMenuPriceCalculator(){
        return new MenuPriceCalculator(500d,10,470d,25,400d);
    }

    private void addRateNTimes(Menu menu,int rate,int nTimes){
        for(int i=0;i<nTimes;i++){
            menu.addRate(rate);
        }
    }
}
