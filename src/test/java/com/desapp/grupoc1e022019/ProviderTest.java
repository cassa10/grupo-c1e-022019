package com.desapp.grupoc1e022019;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class ProviderTest extends TestCase {
    @Test
    public void testWhenICreateANewProviderThenItHasNoMenus(){
        Provider provider = ProviderBuilder.aProvider().build();

        assertEquals(provider.getMenus(),new ArrayList<>());
    }
}
