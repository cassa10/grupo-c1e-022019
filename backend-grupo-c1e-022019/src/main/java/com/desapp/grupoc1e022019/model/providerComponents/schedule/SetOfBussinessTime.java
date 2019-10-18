package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SetOfBussinessTime extends EntityId {

    @OneToMany(
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_bussiness_time")
    private Set<BussinessTime> bussinessTimes = new HashSet<>();

    public SetOfBussinessTime(){

    }

    public SetOfBussinessTime(Set<BussinessTime> bussinessTimes){
        this.bussinessTimes = bussinessTimes;
    }

    public void add(BussinessTime bussinessTime){
        this.bussinessTimes.add(bussinessTime);
    }

    public Set<BussinessTime> getBussinessTimes(){
        return bussinessTimes;
    }

    public boolean containsAll(SetOfBussinessTime set){
        return this.bussinessTimes.containsAll(set.bussinessTimes);
    }
}
