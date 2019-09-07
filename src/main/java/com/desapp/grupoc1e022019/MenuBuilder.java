package com.desapp.grupoc1e022019;

public class MenuBuilder {
    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public Menu build() {
        return new Menu();
    }
}
