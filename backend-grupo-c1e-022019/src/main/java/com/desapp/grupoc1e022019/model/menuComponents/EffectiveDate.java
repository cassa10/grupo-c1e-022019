package com.desapp.grupoc1e022019.model.menuComponents;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
public class EffectiveDate extends EntityId {

    private LocalDate validFrom;
    private LocalDate goodThru;

    public EffectiveDate(LocalDate validFrom,LocalDate goodThru){
        this.validFrom = validFrom;
        this.goodThru = goodThru;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getGoodThru() {
        return goodThru;
    }

    public boolean todayIsBeingAnEffectiveDate() {
        LocalDate today = LocalDate.now();
        return (!validFrom.isAfter(today)) && (! this.goodThru.isBefore(today));
    }
}
