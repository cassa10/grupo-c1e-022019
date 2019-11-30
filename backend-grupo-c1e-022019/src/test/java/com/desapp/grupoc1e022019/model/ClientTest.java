package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.ClientCannotBuyOrderException;
import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.model.clientState.CannotBuyClientState;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {

    @Test
    public void testGivenAClientWithFirstNameJoseAndOtherClientWithFirstNamePepeWhenTheyRecieveGetFirstNameThenTheyGiveTheirFirstNames(){
        Client jose = ClientBuilder.aClient()
                        .withFirstName("Jose").build();

        Client pepe = ClientBuilder.aClient()
                            .withFirstName("Pepe").build();

        Assert.assertEquals(jose.getFirstName(),"Jose");
        Assert.assertEquals(pepe.getFirstName(),"Pepe"); /** No entiendo por que testeas dos veces lo mismo o.O */
    }

    @Test
    public void testGivenAClientWithLastNameAlvearAndOtherClientWithLastNameRocaWhenTheyRecieveGetLastNameThenTheyGiveTheirLastNames(){
        Client clientA = ClientBuilder.aClient()
                                .withLastName("Alvear").build();

        Client clientB = ClientBuilder.aClient()
                                .withLastName("Roca").build();

        Assert.assertEquals(clientA.getLastName(),"Alvear");
        Assert.assertEquals(clientB.getLastName(),"Roca");
    }

    @Test
    public void testGivenAClientWithAddressMitre202QuilmesAndOtherClientWithAddressCorrientes800EzpeletaWhenTheyRecieveGetAddressThenTheyGiveTheirAddress(){
        Client clientA = ClientBuilder.aClient()
                                .withAddress("Mitre 202, Quilmes").build();

        Client clientB = ClientBuilder.aClient()
                                .withAddress("Corrientes 800, Ezpeleta").build();

        Assert.assertEquals(clientA.getAddress(),"Mitre 202, Quilmes");
        Assert.assertEquals(clientB.getAddress(),"Corrientes 800, Ezpeleta");
    }

    @Test
    public void testGivenAClientWithLocationB1878QuilmesBuenosAiresArgentinaAndOtherClientWithLocationB1882EzpeletaBuenosAiresArgentinaWhenTheyReceiveGetLocationThenTheyGiveTheirLocations(){
        Client clientA = ClientBuilder.aClient()
                                .withLocation("B1878, Quilmes, Buenos Aires, Argentina").build();

        Client clientB = ClientBuilder.aClient()
                                .withLocation("B1882, Ezpeleta, Buenos Aires, Argentina").build();

        Assert.assertEquals(clientA.getLocation(),"B1878, Quilmes, Buenos Aires, Argentina");
        Assert.assertEquals(clientB.getLocation(),"B1882, Ezpeleta, Buenos Aires, Argentina");
    }

    @Test
    public void testGivenAClientWithEmailAsdAtDomainDotComAndOtherClientWithEmailPepeAtDomainDotComWhenTheyReceiveGetEmailThenTheyGiveTheirMails(){
        Client clientA = ClientBuilder.aClient()
                .withEmail("asd@domain.com").build();

        Client clientB = ClientBuilder.aClient()
                .withEmail("pepe@domain.com").build();

        Assert.assertEquals(clientA.getEmail(),"asd@domain.com");
        Assert.assertEquals(clientB.getEmail(),"pepe@domain.com");
    }

    @Test
    public void testGivenAClientWithCredit2dAndOtherClientWithCredit0dWhenTheyReceiveGetCreditThenTheyGiveTheirCredit(){
        Client clientA = ClientBuilder.aClient()
                                .withCredit(new Credit(2d)).build();

        Client clientB = ClientBuilder.aClient()
                .withCredit(new Credit (0d)).build();

        Assert.assertEquals(clientA.getCredit(),new Credit(2d));
        Assert.assertEquals(clientB.getCredit(),new Credit(0d));
    }

    @Test
    public void testGivenAClientWithPhoneNumber1102232234AndOtherClientWithPhoneNumber1143214321WhenTheyRecieveGetPhoneNumberThenTheyGiveTheirPhoneNumber(){
        Client clientA = ClientBuilder.aClient()
                .withPhoneNumber("1102232234").build();

        Client clientB = ClientBuilder.aClient()
                .withPhoneNumber("1143214321").build();

        Assert.assertEquals(clientA.getPhoneNumber(),"1102232234");
        Assert.assertEquals(clientB.getPhoneNumber(),"1143214321");
    }

    @Test
    public void testGivenAClientWith0dCreditDeposits20PesosThenTheClientHas20PesosInHisAccount(){
        Client client = ClientBuilder.aClient()
                .withCredit(new Credit(0d)).build();

        client.deposit(new Credit(20d));

        Assert.assertEquals(client.getCredit(),new Credit(20d));
    }

    @Test
    public void testGivenAClientWith12reditsWhenIDebit5creditsTheClientHas7Credits(){
        Client client = ClientBuilder.aClient()
                .withCredit(new Credit(12d)).build();

        client.debit(new Credit(5d));

        Assert.assertEquals(client.getCredit(),new Credit(7d));
    }
    @Test
    public void testGivenAClientWith5creditsWhenIDebit5creditsTheClientHas0Credits(){
        Client client = ClientBuilder.aClient()
                .withCredit(new Credit(5d)).build();

        client.debit(new Credit(5d));

        Assert.assertEquals(client.getCredit(),new Credit(0d));
    }

    @Test(expected = InsufficientCreditException.class)
    public void testGivenAClientWith5creditsWhenITryToDebit6creditsThenItRaiseInsufficientCreditException(){
        Client client = ClientBuilder.aClient()
                .withCredit(new Credit(5d)).build();

        client.debit(new Credit(6d));


    }
    @Test
    public void testGivenAClientWith5creditsWhenITryToDebit6creditsThenTheClientHas5Credits(){
        Client client = ClientBuilder.aClient()
                .withCredit(new Credit(5d)).build();
        try {
            client.debit(new Credit(6d));
        }catch (InsufficientCreditException e){
            Assert.assertEquals(e.getMessage(),"Client doesn't have enough credits");
        }
        Assert.assertEquals(client.getCredit(),new Credit(5d));
    }
    @Test
    public void testGivenAClientByDefaultWithNormalStateWhenRecievesIsNormalClientReturnsTrueAndClientHaveToRankAndIsCannotBuyClientBothReturnFalse(){
        Client client = ClientBuilder.aClient().build();

        Assert.assertTrue(client.isNormalClient());
        Assert.assertFalse(client.isClientHaveToRank());
        Assert.assertFalse(client.isCannotBuyClient());
    }
    @Test
    public void testGivenAClientWithCannotBuyClientStateWhenRecievesIsNormalClientReturnsFalseAndClientHaveToRankAndIsCannotBuyClientBothReturnTrue(){
        Client client = ClientBuilder.aClient().withStateClient(new CannotBuyClientState()).build();

        Assert.assertFalse(client.isNormalClient());
        Assert.assertTrue(client.isClientHaveToRank());
        Assert.assertTrue(client.isCannotBuyClient());
    }
    @Test(expected = InsufficientCreditException.class)
    public void testGivenAClientByDefaultWithNormalStateAndCreditZeroWhenClientBuysAnOrderWithMenuPriceWithAmount2Is20ThenArraysAnInsufficientCreditException(){
        Client client = ClientBuilder.aClient().build();

        Menu anMenu = MenuBuilder.aMenu().withMenuPriceCalculator(menuPriceWithAmount2Is20()).build();
        Order anOrder = OrderBuilder.anOrder().withClient(client).withMenusAmount(2).withMenu(anMenu).build();
        client.buyAnOrder(anOrder);
    }

    @Test
    public void testGivenAClientByDefaultWithNormalStateAnd10CreditsWhenClientCancellAnOrderWithMenuPriceWithAmount2Is20ThenClientRecoversAllCreditsPaidWhichWas20SoClientHas30Credits(){
        Client client = ClientBuilder.aClient().withCredit(new Credit(10d)).build();

        Menu anMenu = MenuBuilder.aMenu().withMenuPriceCalculator(menuPriceWithAmount2Is20()).build();
        Order anOrder = OrderBuilder.anOrder().withClient(client).withMenusAmount(2).withMenu(anMenu).build();
        client.cancellOrder(anOrder);

        Assert.assertEquals(client.getCredit(),new Credit(30d));
    }

    @Test
    public void testGivenAClientCannotBuyStateAnd10CreditsWhenClientCancellAnOrderWithMenuPriceWithAmount2Is20ThenClientRecoversAllCreditsPaidWhichWas20SoClientHas30Credits(){
        Client client = ClientBuilder.aClient().withStateClient(new CannotBuyClientState()).withCredit(new Credit(10d)).build();

        Menu anMenu = MenuBuilder.aMenu().withMenuPriceCalculator(menuPriceWithAmount2Is20()).build();
        Order anOrder = OrderBuilder.anOrder().withClient(client).withMenusAmount(2).withMenu(anMenu).build();
        client.cancellOrder(anOrder);

        Assert.assertEquals(client.getCredit(),new Credit(30d));
    }

    @Test
    public void testGivenAClientByDefaultWithNormalStateAnd20CreditsWhenClientBuyAnOrderWithMenuPriceWithAmount2Is20ThenClientHasBeenDebited20CreditsSoTheirCreditIsZero(){
        Client client = ClientBuilder.aClient().withCredit(new Credit(20d)).build();

        Menu anMenu = MenuBuilder.aMenu().withMenuPriceCalculator(menuPriceWithAmount2Is20()).build();
        Order anOrder = OrderBuilder.anOrder().withClient(client).withMenusAmount(2).withMenu(anMenu).build();
        client.buyAnOrder(anOrder);
        Assert.assertEquals(client.getCredit(),new Credit(0d));
    }

    @Test(expected = ClientCannotBuyOrderException.class)
    public void testGivenAClientWithCannotBuyStateAnd1000CreditsWhenClientBuyAnOrderWithMenuPriceWithAmount2Is20ThenExceptionClientCannotBuyOrderArrays(){
        Client client = ClientBuilder.aClient().withStateClient(new CannotBuyClientState()).withCredit(new Credit(1000d)).build();

        Menu anMenu = MenuBuilder.aMenu().withMenuPriceCalculator(menuPriceWithAmount2Is20()).build();
        Order anOrder = OrderBuilder.anOrder().withClient(client).withMenusAmount(2).withMenu(anMenu).build();

        client.buyAnOrder(anOrder);
    }

    private MenuPriceCalculator menuPriceWithAmount2Is20(){
        return new MenuPriceCalculator(10d,5,17d,7,9d);
    }
}
