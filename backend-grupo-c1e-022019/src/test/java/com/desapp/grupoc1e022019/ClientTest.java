package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.Client;
import com.desapp.grupoc1e022019.model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {

    @Test
    public void testGivenAClientWithFirstNameJoseAndOtherClientWithFirstNamePepeWhenTheyRecieveGetFirstNameThenTheyGiveTheirFirstNames(){
        Client jose = new ClientBuilder().aClient()
                        .withFirstName("Jose").build();

        Client pepe = new ClientBuilder().aClient()
                            .withFirstName("Pepe").build();

        Assert.assertEquals(jose.getFirstName(),"Jose");
        Assert.assertEquals(pepe.getFirstName(),"Pepe");
    }

    @Test
    public void testGivenAClientWithLastNameAlvearAndOtherClientWithLastNameRocaWhenTheyRecieveGetLastNameThenTheyGiveTheirLastNames(){
        Client clientA = new ClientBuilder().aClient()
                                .withLastName("Alvear").build();

        Client clientB = new ClientBuilder().aClient()
                                .withLastName("Roca").build();

        Assert.assertEquals(clientA.getLastName(),"Alvear");
        Assert.assertEquals(clientB.getLastName(),"Roca");
    }

    @Test
    public void testGivenAClientWithAddressMitre202QuilmesAndOtherClientWithAddressCorrientes800EzpeletaWhenTheyRecieveGetAddressThenTheyGiveTheirAddress(){
        Client clientA = new ClientBuilder().aClient()
                                .withAddress("Mitre 202, Quilmes").build();

        Client clientB = new ClientBuilder().aClient()
                                .withAddress("Corrientes 800, Ezpeleta").build();

        Assert.assertEquals(clientA.getAddress(),"Mitre 202, Quilmes");
        Assert.assertEquals(clientB.getAddress(),"Corrientes 800, Ezpeleta");
    }

    @Test
    public void testGivenAClientWithLocationB1878QuilmesBuenosAiresArgentinaAndOtherClientWithLocationB1882EzpeletaBuenosAiresArgentinaWhenTheyReceiveGetLocationThenTheyGiveTheirLocations(){
        Client clientA = new ClientBuilder().aClient()
                                .withLocation("B1878, Quilmes, Buenos Aires, Argentina").build();

        Client clientB = new ClientBuilder().aClient()
                                .withLocation("B1882, Ezpeleta, Buenos Aires, Argentina").build();

        Assert.assertEquals(clientA.getLocation(),"B1878, Quilmes, Buenos Aires, Argentina");
        Assert.assertEquals(clientB.getLocation(),"B1882, Ezpeleta, Buenos Aires, Argentina");
    }

    @Test
    public void testGivenAClientWithEmailAsdAtDomainDotComAndOtherClientWithEmailPepeAtDomainDotComWhenTheyReceiveGetEmailThenTheyGiveTheirMails(){
        Client clientA = new ClientBuilder().aClient()
                .withEmail("asd@domain.com").build();

        Client clientB = new ClientBuilder().aClient()
                .withEmail("pepe@domain.com").build();

        Assert.assertEquals(clientA.getEmail(),"asd@domain.com");
        Assert.assertEquals(clientB.getEmail(),"pepe@domain.com");
    }

    @Test
    public void testGivenAClientWithCredit2dAndOtherClientWithCredit0dWhenTheyReceiveGetCreditThenTheyGiveTheirCredit(){
        Client clientA = new ClientBuilder().aClient()
                                .withCredit(2d).build();

        Client clientB = new ClientBuilder().aClient()
                .withCredit(0d).build();

        Assert.assertEquals(clientA.getCredit(),new Double(2d));
        Assert.assertEquals(clientB.getCredit(),new Double(0d));
    }

    @Test
    public void testGivenAClientWithPhoneNumber1102232234AndOtherClientWithPhoneNumber1143214321WhenTheyRecieveGetPhoneNumberThenTheyGiveTheirPhoneNumber(){
        Client clientA = new ClientBuilder().aClient()
                .withPhoneNumber("1102232234").build();

        Client clientB = new ClientBuilder().aClient()
                .withPhoneNumber("1143214321").build();

        Assert.assertEquals(clientA.getPhoneNumber(),"1102232234");
        Assert.assertEquals(clientB.getPhoneNumber(),"1143214321");
    }
}
