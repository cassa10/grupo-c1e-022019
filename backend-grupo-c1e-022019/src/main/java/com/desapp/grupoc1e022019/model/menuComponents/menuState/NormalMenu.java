package com.desapp.grupoc1e022019.model.menuComponents.menuState;

import com.desapp.grupoc1e022019.model.Menu;
import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;

public class NormalMenu extends MenuState {

    @Override
    public void addRate(Menu menu,RankAverageMenu menuRank, Integer score){
        menuRank.addRating(score);
        if(menuRank.hasMoreTwentyRatesAmountAndAverageIsLessThanTwo()){
            menu.setMenuState(new CancelledMenu());
            menu.penalizeProvider();
            //TODO
            // CALL SERVICE, DELETE MENU, ONLY CHANGE STATE IN BD OR WHAT??
        }
    }

    @Override
    public boolean isNormal(){
        return true;
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
}