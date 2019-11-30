package com.desapp.grupoc1e022019.model.menuComponents.menuState;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;

import javax.persistence.Entity;

@Entity
public class NormalMenu extends MenuState {

    @Override
    public void addRate(Menu menu,RankAverageMenu menuRank, Integer score){
        menuRank.addRating(score);
        if(menuRank.hasMoreTwentyRatesAmountAndAverageIsLessThanTwo()){
            menu.setMenuState(new CancelledMenu());
            menu.penalizeProvider();
        }
    }

    @Override
    public boolean isNormal(){
        return true;
    }

    @Override
    public String toString(){
        return "NormalMenu";
    }
}
