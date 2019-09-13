package com.desapp.grupoc1e022019;

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

}
