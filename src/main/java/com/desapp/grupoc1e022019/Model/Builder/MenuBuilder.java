package com.desapp.grupoc1e022019.Model.Builder;

import com.desapp.grupoc1e022019.Model.Menu;

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
