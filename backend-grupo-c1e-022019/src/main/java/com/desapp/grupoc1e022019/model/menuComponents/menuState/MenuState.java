package com.desapp.grupoc1e022019.model.menuComponents.menuState;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;

public abstract class MenuState {

    public void addRate(Menu menu, RankAverageMenu menuRank, Integer score){}

    public boolean isCancelled(){return false;}

    public boolean isNormal(){return false;}

    public abstract String toString();
}
