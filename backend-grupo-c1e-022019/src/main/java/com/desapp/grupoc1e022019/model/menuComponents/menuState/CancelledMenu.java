package com.desapp.grupoc1e022019.model.menuComponents.menuState;

import javax.persistence.Entity;

@Entity
public class CancelledMenu extends MenuState {

    @Override
    public boolean isCancelled(){return true;}

    @Override
    public String toString(){
        return "CANCELLED";
    }
}
