package com.desapp.grupoc1e022019.model.menuComponents.menuState;

import com.desapp.grupoc1e022019.model.EntityId;
import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;

import javax.persistence.Entity;

@Entity
public abstract class MenuState extends EntityId {

    public void addRate(Menu menu, RankAverageMenu menuRank, Integer score){}

    public boolean isCancelled(){return false;}

    public boolean isNormal(){return false;}

    @Override
    public abstract String toString();
}
