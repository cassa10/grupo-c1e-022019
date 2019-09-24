package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.Order;
import com.desapp.grupoc1e022019.model.orderComponents.orderState.*;
import com.desapp.grupoc1e022019.services.builder.OrderBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class OrderTest {
    @Test
    public void testWhenICreateANewOrderHisStateIsPending(){
        Order newOrder = OrderBuilder.anOrder().build();

        Assert.assertTrue(newOrder.isStatePending());
        Assert.assertEquals(newOrder.getStateName(),"PENDING");

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
        Assert.assertEquals(newOrder.getStateName(),"CONFIRMED");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateSending());
    }

    @Test
    public void testAnOrderWithPendingStateIsConfirmedAfterTwelveOClock(){
        Order newOrder = OrderBuilder.anOrder().build();

        ViendasYa viendasYa = new ViendasYa();
        viendasYa.attachToObserver(newOrder);

        //It's 12 o'Clock
        viendasYa.notifyOrders(anyDateAtMidnight());

        Assert.assertTrue(newOrder.isStateConfirmed());

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateSending());
    }

    @Test
    public void testAllOrdersWithPendingStateAreConfirmedAfterTwelveOClock(){
        Order newOrder = OrderBuilder.anOrder().build();
        Order newOrder2 = OrderBuilder.anOrder().withState(new DeliveredOrder()).build();
        Order newOrder3 = OrderBuilder.anOrder().build();
        ViendasYa viendasYa = new ViendasYa();

        viendasYa.attachToObserver(newOrder);
        viendasYa.attachToObserver(newOrder2);
        viendasYa.attachToObserver(newOrder3);

        //It's 12 o'Clock
        viendasYa.notifyOrders(anyDateAtMidnight());

        Assert.assertTrue(newOrder.isStateConfirmed());

        Assert.assertTrue(newOrder2.isStateDelivered());

        Assert.assertTrue(newOrder3.isStateConfirmed());
    }

    @Test
    public void testAllOrdersWithPendingStateAreNotConfirmedBeforeTwelveOClock(){
        Order newOrder = OrderBuilder.anOrder().build();

        Order newOrder2 = OrderBuilder.anOrder().withState(new DeliveredOrder()).build();

        Order newOrder3 = OrderBuilder.anOrder().build();

        ViendasYa viendasYa = new ViendasYa();
        viendasYa.attachToObserver(newOrder);
        viendasYa.attachToObserver(newOrder2);
        viendasYa.attachToObserver(newOrder3);

        //It isn't 12 o'Clock
        viendasYa.notifyOrders(anyDateNotAtMidnight());

        Assert.assertTrue(newOrder.isStatePending());
        Assert.assertTrue(newOrder2.isStateDelivered());
        Assert.assertTrue(newOrder3.isStatePending());
    }

    @Test
    public void testAllOrdersWithPendingOrderAreNotConfirmedBeforeTwelveOClockButWhenItsAfterTwelveOClockAllPendingOrdersAreConfirmed(){
        Order newOrder = OrderBuilder.anOrder().build();

        Order newOrder2 = OrderBuilder.anOrder().withState(new DeliveredOrder()).build();

        Order newOrder3 = OrderBuilder.anOrder().build();

        ViendasYa viendasYa = new ViendasYa();
        viendasYa.attachToObserver(newOrder);
        viendasYa.attachToObserver(newOrder2);
        viendasYa.attachToObserver(newOrder3);

        //It isn't 12 o'Clock
        viendasYa.notifyOrders(anyDateNotAtMidnight());

        Assert.assertTrue(newOrder.isStatePending());
        Assert.assertTrue(newOrder2.isStateDelivered());
        Assert.assertTrue(newOrder3.isStatePending());

        //It is midnight
        viendasYa.notifyOrders(anyDateAtMidnight());

        Assert.assertTrue(newOrder.isStateConfirmed());
        Assert.assertTrue(newOrder2.isStateDelivered());
        Assert.assertTrue(newOrder3.isStateConfirmed());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsConfirmedAndSentThisHasStateSending(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.confirmed();
        newOrder.sending();

        Assert.assertTrue(newOrder.isStateSending());
        Assert.assertEquals(newOrder.getStateName(),"SENDING");

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
        Assert.assertEquals(newOrder.getStateName(),"DELIVERED");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateSending());
        Assert.assertFalse(newOrder.isStateConfirmed());
    }
    @Test
    public void testWhenICancelANewOrderThenItsNewStateIsCancelled(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.cancelled();

        Assert.assertTrue(newOrder.isStateCancelled());
        Assert.assertEquals(newOrder.getStateName(),"CANCELLED");

        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateRanked());
        Assert.assertFalse(newOrder.isStateSending());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStateConfirmed());
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
        Order newOrder = OrderBuilder.anOrder().build();
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

        Assert.assertFalse(newOrder.isStateCancelled());
        Assert.assertFalse(newOrder.isStateConfirmed());
        Assert.assertFalse(newOrder.isStateDelivered());
        Assert.assertFalse(newOrder.isStatePending());
        Assert.assertFalse(newOrder.isStateSending());
    }

    private LocalDateTime anyDateAtMidnight(){
        LocalDateTime now = LocalDateTime.now();
        return now.withHour(0);
    }

    private LocalDateTime anyDateNotAtMidnight(){
        LocalDateTime now = LocalDateTime.now();
        return now.withHour(1);
    }

    /** TODO
             Debatir: Los estados deberian pasarse unos a otros? Es decir, deberia poder
            setearse "Delivered" en cualquier momento o solo despues de "Sending"?
    */

}
