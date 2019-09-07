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
    public void testWhenAProviderAddANewMenuThenItHasOneMoreMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu = MenuBuilder.aMenu().build();

        provider.addMenu(newMenu);

        assertEquals(provider.getMenus().size(),1);
    }
    @Test(expected = MaximumMenusSizeException.class)
    public void testWhenAProviderAddANewMenuHaving20ItCannotAddMoreThenItRaiseMaxCantException()  {
        ArrayList<Menu>  twentyMenus = getTwentyRandomMenus();
        Provider provider = ProviderBuilder.aProvider().withMenus(twentyMenus).build();
        Menu newMenu = MenuBuilder.aMenu().build();
        try {
            provider.addMenu(newMenu);
        }
        catch(MaximumMenusSizeException e){

        }

        assertEquals(provider.getMenus().size(),20);
    }

    private ArrayList<Menu> getTwentyRandomMenus() {
        ArrayList<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            menus.add(MenuBuilder.aMenu().build());
        }
        return menus;
    }

    public void testWhenProviderDeletesAMenuThenItHasOneLessMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu dummyMenu = MenuBuilder.aMenu().build();
        provider.addMenu(dummyMenu);

        provider.deleteMenu(dummyMenu);

        assertTrue(provider.getMenus().isEmpty());
    }


}
