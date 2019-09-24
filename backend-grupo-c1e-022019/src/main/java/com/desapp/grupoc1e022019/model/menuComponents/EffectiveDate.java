package com.desapp.grupoc1e022019.model.menuComponents;

import java.time.LocalDate;

public class EffectiveDate {

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
        return validFrom.isAfter(today) && this.goodThru.isBefore(today);
    }
}
