package com.desapp.grupoc1e022019.model.builder;

import com.desapp.grupoc1e022019.model.Menu;

public class MenuBuilder {
    private Integer id;

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public Menu build() {
        return new Menu(id);
    }

    public MenuBuilder withId(Integer id) {
        this.id = id;
        return this;
    }
}
