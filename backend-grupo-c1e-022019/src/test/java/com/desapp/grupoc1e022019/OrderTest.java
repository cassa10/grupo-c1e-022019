package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.exception.RatingForbiddenException;
import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.observer.TimeObservable;
import com.desapp.grupoc1e022019.model.orderState.*;
import com.desapp.grupoc1e022019.model.builder.OrderBuilder;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Timer;

public class OrderTest {
    @Test
    public void testWhenICreateANewOrderHisStateIsPending(){
        Order newOrder = OrderBuilder.anOrder().build();

        Assert.assertEquals(newOrder.getState(), new PendingOrder());
    }

    @Test
    public void testWhenISetAConfirmedStateToANewOrderItNowHasConfirmedState(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.setState(new ConfirmedOrder());

        Assert.assertEquals(newOrder.getState(), new ConfirmedOrder());
    }

    @Test
    public void testAnOrderWithPendingStateIsConfirmedAfterTwelveOClock(){
        Order newOrder = OrderBuilder.anOrder().build();
        TimeObservable timeObservable = new TimeObservable();
        timeObservable.attach(newOrder);

        //It's 12 o'Clock
        timeObservable.updateObservers();

        Assert.assertEquals(newOrder.getState(), new ConfirmedOrder());
    }
    @Test
    public void testAllOrdersWithPendingStateAreConfirmedAfterTwelveOClock(){
        Order newOrder = OrderBuilder.anOrder().build();
        Order newOrder2 = OrderBuilder.anOrder().build();
        newOrder2.setState(new DeliveredOrder());
        Order newOrder3 = OrderBuilder.anOrder().build();
        TimeObservable timeObservable = new TimeObservable();
        timeObservable.attach(newOrder);
        timeObservable.attach(newOrder2);
        timeObservable.attach(newOrder3);

        //It's 12 o'Clock
        timeObservable.updateObservers();

        Assert.assertEquals(newOrder.getState(), new ConfirmedOrder());
        Assert.assertEquals(newOrder2
                .getState(), new DeliveredOrder());
        Assert.assertEquals(newOrder3.getState(), new ConfirmedOrder());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsSentHisStateIsSending(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.sending();

        Assert.assertEquals(newOrder.getState(), new SendingOrder());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsDeliveredHisStateIsDelivered(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.delivered();

        Assert.assertEquals(newOrder.getState(), new DeliveredOrder());
    }
    @Test
    public void testWhenICancelANewOrderThenItsNewStateIsCancelled(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.cancelled();

        Assert.assertEquals(newOrder.getState(), new CancelledOrder());
    }

    @Test
    public void testWhenICreateANewOrderThenItHasNoStars(){
        Order newOrder = OrderBuilder.anOrder().build();

        Assert.assertEquals(newOrder.getStars(), new Double(0));
    }
    @Test
    public void testWhenIRateANewOrderWithFiveStarsThenItHasFiveStars(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.delivered();

        newOrder.rate(5);

        Assert.assertEquals(newOrder.getStars(), new Double(5));
    }

    @Test
    public void testWhenIRateANewOrderWithFiveStarsAndFourStarsThenItHasFourPointFiveStars(){
        Order newOrder = OrderBuilder.anOrder().build();
        newOrder.delivered();

        newOrder.rate(5);
        newOrder.rate(4);

        Assert.assertEquals(newOrder.getStars(), new Double(4.5));
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

    /** TODO
             Debatir: Los estados deberian pasarse unos a otros? Es decir, deberia poder
            setearse "Delivered" en cualquier momento o solo despues de "Sending"?
    */

}
