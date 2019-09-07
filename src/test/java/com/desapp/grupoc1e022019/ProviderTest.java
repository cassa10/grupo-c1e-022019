package com.desapp.grupoc1e022019;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ProviderTest {
    @Test
    public void testWhenICreateANewProviderThenItHasNoMenus(){
        Provider provider = ProviderBuilder.aProvider().build();

        Assert.assertEquals(provider.getMenus(), new ArrayList<>());
    }
    @Test
    public void testWhenAProviderAddANewMenuThenItHasOneMoreMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu = MenuBuilder.aMenu().withId(0).build();

        provider.addMenu(newMenu);

        Assert.assertEquals(provider.getMenus().size(), 1);
    }
    @Test(expected = MaximumMenusSizeException.class)
    public void testWhenAProviderAddANewMenuHaving20ItCannotAddMoreThenItRaiseMaxCantException()  {
        ArrayList<Menu>  twentyMenus = getTwentyRandomMenus();
        Provider provider = ProviderBuilder.aProvider().withMenus(twentyMenus).build();
        Menu newMenu = MenuBuilder.aMenu().build();

        provider.addMenu(newMenu);

    }

    @Test(expected = RepeatedIDException.class)
    public void testWhenAProviderAddAMenuButTheIdAlreadyExistsThenItRaiseRepeatedIDException()  {
        Provider provider = ProviderBuilder.aProvider().build();
        Menu newMenu1 = MenuBuilder.aMenu().withId(1).build();
        Menu newMenu2 = MenuBuilder.aMenu().withId(1).build();
        provider.addMenu(newMenu1);

        provider.addMenu(newMenu2);

    }

    private ArrayList<Menu> getTwentyRandomMenus() {
        ArrayList<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            menus.add(MenuBuilder.aMenu().build());
        }
        return menus;
    }

    @Test
    public void testWhenProviderDeletesAMenuThenItHasOneLessMenu(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu dummyMenu = MenuBuilder.aMenu().build();
        provider.addMenu(dummyMenu);

        provider.deleteMenu(dummyMenu);

        Assert.assertTrue(provider.getMenus().isEmpty());
    }

    @Test
    public void testWhenProviderUpdatesAMenuThenItHasTheSameNumberOfMenus(){
        Provider provider = ProviderBuilder.aProvider().build();
        Menu oldMenu = MenuBuilder.aMenu().withId(1).build();
        Menu updatedMenu = MenuBuilder.aMenu().withId(1).build();
        provider.addMenu(oldMenu);

        provider.updateMenu(1,updatedMenu);

        Assert.assertTrue(provider.getMenus().contains(updatedMenu));
    }


}
