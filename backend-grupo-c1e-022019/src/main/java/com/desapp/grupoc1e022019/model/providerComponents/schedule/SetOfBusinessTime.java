package com.desapp.grupoc1e022019.model.providerComponents.schedule;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class SetOfBusinessTime extends EntityId {

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "id_set_of_bussiness_time")
    private Set<BusinessTime> businessTimes = new HashSet<>();

    public SetOfBusinessTime(){}

    public SetOfBusinessTime(Set<BusinessTime> businessTimes){
        this.businessTimes = businessTimes;
    }

    public void add(BusinessTime businessTime){
            this.businessTimes.add(businessTime);
    }

    public Set<BusinessTime> getBusinessTimes(){
        return businessTimes;
    }

    public boolean containsAll(SetOfBusinessTime set){
        return this.businessTimes.containsAll(set.businessTimes);
    }

    public void setBusinessTimes(Set<BusinessTime> businessTimes) {
        this.businessTimes = businessTimes;
    }

    public boolean hasBussinessIntersection() {
        boolean value = false;

        if(! businessTimes.isEmpty()){
            BusinessTime b1;

            Iterator<BusinessTime> iterator = businessTimes.iterator();
            while (iterator.hasNext()){
                b1 = iterator.next();
                value = value || b1.hasIntersection(iterator);
            }

            return value;
        }

        return false;
    }

    public boolean allBusinessHourAreValid() {
        return businessTimes.stream().allMatch(BusinessTime::isValidBusinessTime);
    }
}
