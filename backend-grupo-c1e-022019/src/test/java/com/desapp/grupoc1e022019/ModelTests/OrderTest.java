package com.desapp.grupoc1e022019.ModelTests;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.menuComponents.MenuPriceCalculator;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.Delivery;
import com.desapp.grupoc1e022019.model.orderComponents.deliverType.PickUp;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.*;
import com.desapp.grupoc1e022019.services.builder.ClientBuilder;
import com.desapp.grupoc1e022019.services.builder.MenuBuilder;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class OrderTest {
    @Test
    public void testWhenICreateANewOrderHisStateIsPending(){
        Order newOrder = OrderBuilder.anOrder().build();

        Assert.assertTrue(newOrder.isStatePending());
        Assert.assertEquals(newOrder.getStateName(),"PendingOrder");

        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateConfirmed());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateSending());
    }

    @Test
    public void testWhenItIsConfirmedThenItHasConfirmedState(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();

        Assert.assertTrue(newOrder.isStateConfirmed());
        Assert.assertEquals(newOrder.getStateName(),"ConfirmedOrder");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateSending());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsConfirmedAndSentThisHasStateSending(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();

        Assert.assertTrue(newOrder.isStateSending());
        Assert.assertEquals(newOrder.getStateName(),"SendingOrder");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateConfirmed());
    }

    @Test
    public void testWhenICreateANewOrderAndThenWasConfirmed_SentAndDeliveredThisHasStateDelivered(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();
        newOrder.delivered();

        Assert.assertTrue(newOrder.isStateDelivered());
        Assert.assertEquals(newOrder.getStateName(),"DeliveredOrder");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateSending());
        Assert.assertFalse(newOrder.isStateConfirmed());
    }
    @Test
    public void testWhenICancelANewOrderWithOrderAmount5AndMenuPrice10WithAmount5ThenOrderStateIsCancelledAndDeposit10CreditsWhichWasPaidInClientAccount(){
        Order newOrder = OrderBuilder.anOrder().withClient(ClientBuilder.aClient().withCredit(new Credit(10d)).build()).
                                                withMenusAmount(5).
                                                withMenu(anyMenuWithPrice10With5Amount()).
                                                build();
        newOrder.cancelled();

        Assert.assertTrue(newOrder.isStateCancelled());
        Assert.assertEquals(newOrder.getStateName(),"CancelledOrder");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateSending());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateConfirmed());

        Assert.assertEquals(newOrder.getClient().getCredit(),new Credit(15d));
    }

    @Test
    public void testGivenAnOrderWithAnyStateExceptPendingWhenIWantCancelIt_ItCannotBeCancelledAndItHasTheirPreviousState(){
        Order newOrder = OrderBuilder.anOrder().withState(new ConfirmedOrder()).build();
        Order newOrder2 = OrderBuilder.anOrder().withState(new SendingOrder()).build();
        Order newOrder3 = OrderBuilder.anOrder().withState(new DeliveredOrder()).build();
        Order newOrder4 = OrderBuilder.anOrder().withState(new RankedOrder()).build();

        newOrder.cancelled();

        Assert.assertTrue(newOrder.isStateConfirmed());
        Assert.assertFalse(newOrder.isStateCancelled());

        newOrder2.cancelled();

        Assert.assertTrue(newOrder2.isStateSending());
        Assert.assertFalse(newOrder2.isStateCancelled());

        newOrder3.cancelled();

        Assert.assertTrue(newOrder3.isStateDelivered());
        Assert.assertFalse(newOrder3.isStateCancelled());

        newOrder4.cancelled();

        Assert.assertTrue(newOrder4.isStateRanked());
        Assert.assertFalse(newOrder4.isStateCancelled());

    }

    @Test
    public void testWhenICreateANewOrderThenItHasNoStars(){
        Order newOrder = OrderBuilder.anOrder().build();

        Assert.assertEquals(newOrder.getStars(), new Integer(0));
    }
    @Test
    public void testWhenIRateANewOrderWithFiveStarsThenItHasFiveStars(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();
        newOrder.delivered();

        newOrder.rate(5);

        Assert.assertEquals(newOrder.getStars(), new Integer(5));
    }

    @Test(expected = RatingForbiddenException.class)
    public void testWhenIRateANewOrderDeliveredWithFiveStarsAndITryToRateAgainThenItRaiseRatingForbiddenException(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();
        newOrder.delivered();

        newOrder.rate(5);
        newOrder.rate(4);
    }

    @Test(expected = RatingForbiddenException.class)
    public void testWhenITryToRateAPendingOrderItRaiseRatingForbiddenException(){
        Order newOrder = OrderBuilder.anOrder().build();
        //It's state is Pending by default

        newOrder.rate(5);
    }
    @Test(expected = RatingForbiddenException.class)
    public void testWhenITryToRateASendingOrderItRaiseRatingForbiddenException(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.sending();

        newOrder.rate(5);
    }
    @Test(expected = RatingForbiddenException.class)
    public void testWhenITryToRateACancelledOrderItRaiseRatingForbiddenException(){
        Order newOrder = OrderBuilder.anOrder().withMenusAmount(1).withMenu(anyMenuWithPrice5With1Amount()).build();
        newOrder.cancelled();

        newOrder.rate(5);
    }

    @Test(expected = RatingForbiddenException.class)
    public void testWhenITryToRateAConfirmedOrderItRaiseRatingForbiddenException(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();

        newOrder.rate(5);
    }

    @Test
    public void testWhenICreateANewOrderAndWasConfirmedAndSentAndDeliveredAndRankedThenTheOrderStateIsRanked(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();
        newOrder.delivered();

        newOrder.rate(4);

        Assert.assertTrue(newOrder.isStateRanked());
        Assert.assertEquals(newOrder.getStateName(),"RankedOrder");

        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateConfirmed());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateSending());
    }

    @Test
    public void testGivenOrderWithStateRankedWhenRecievesDeliveredNothingHappens(){
        Order newOrder = OrderBuilder.anOrder().withState(new RankedOrder()).build();
        newOrder.delivered();

        Assert.assertTrue(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateDelivered());
    }

    @Test
    public void testGivenOrderWithDeliverTypeDeliveryWhenOrderRecievesIsDeliveryIsTrueAndClientHaveToPickUpIsFalse(){
        Order newOrder = OrderBuilder.anOrder().withDeliverType(new Delivery()).build();

        Assert.assertTrue(newOrder.isDelivery());
        Assert.assertFalse(newOrder.isPickUpDeliver());
    }

    @Test
    public void testGivenOrderWithDeliverTypePickUpWhenOrderRecievesIsDeliveryIsFalseAndClientHaveToPickUpIsTrue(){
        Order newOrder = OrderBuilder.anOrder().withDeliverType(new PickUp()).build();

        Assert.assertTrue(newOrder.isPickUpDeliver());
        Assert.assertFalse(newOrder.isDelivery());
    }

    private LocalDateTime anyDateAtMidnight(){
        LocalDateTime now = LocalDateTime.now();
        return now.withHour(0);
    }

    private LocalDateTime anyDateNotAtMidnight(){
        LocalDateTime now = LocalDateTime.now();
        return now.withHour(1);
    }

    private Menu anyMenuWithPrice5With1Amount(){
        MenuPriceCalculator priceCalculator = new MenuPriceCalculator(5d,5,4d,7,3d);
        return MenuBuilder.aMenu().withMenuPriceCalculator(priceCalculator).build();
    }

    private Menu anyMenuWithPrice10With5Amount(){
        MenuPriceCalculator priceCalculator = new MenuPriceCalculator(10d,2,7d,5,1d);
        return MenuBuilder.aMenu().withMenuPriceCalculator(priceCalculator).build();
    }
}
