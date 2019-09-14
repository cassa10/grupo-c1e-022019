package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.*;
import com.desapp.grupoc1e022019.model.orderState.DeliveredOrder;
import com.desapp.grupoc1e022019.model.orderState.PendingOrder;
import com.desapp.grupoc1e022019.model.orderState.SendingOrder;
import com.desapp.grupoc1e022019.model.builder.OrderBuilder;
import junit.framework.TestCase;
import org.junit.Test;

public class OrderTest extends TestCase {
    @Test
    public void testWhenICreateANewOrderHisStateIsPending(){
        Order newOrder = OrderBuilder.anOrder().build();

        assertEquals(newOrder.getState(),new PendingOrder());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsSentHisStateIsSending(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.sending();

        assertEquals(newOrder.getState(),new SendingOrder());
    }

    @Test
    public void testWhenICreateANewOrderAndThenItsDeliveredHisStateIsDelivered(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.delivered();

        assertEquals(newOrder.getState(),new DeliveredOrder());
    }

    @Test
    public void testWhenICreateANewOrderThenItHasNoStars(){
        Order newOrder = OrderBuilder.anOrder().build();

        assertEquals(newOrder.getStars(),new Double(0));
    }
    @Test
    public void testWhenIRateANewOrderWithFiveStarsThenItHasFiveStars(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.rate(5);

        assertEquals(newOrder.getStars(),new Double(5));
    }

    @Test
    public void testWhenIRateANewOrderWithFiveStarsAndFourStarsThenItHasFourPointFiveStars(){
        Order newOrder = OrderBuilder.anOrder().build();

        newOrder.rate(5);
        newOrder.rate(4);

        assertEquals(newOrder.getStars(),new Double(4.5));
    }

}
