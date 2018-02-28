package com.disney.composite.api;

import java.util.Date;

import org.testng.annotations.Test;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        Date currentDate = new Date();
        Date effectiveFrom = new Date(currentDate.getTime() - (10 * 24 * 60 * 60 * 1000));
        // Date effectiveTo = null;
        Date effectiveTo = new Date(currentDate.getTime() + (10 * 24 * 60 * 60 * 1000));
        System.out.println(isActive(currentDate, effectiveFrom, effectiveTo));
    }

    private boolean isActive(
            final Date currentDate,
            final Date startDate,
            final Date effectiveTo) {
        return (startDate == null || currentDate.compareTo(startDate) >= 0)
                && (effectiveTo == null
                        || currentDate.compareTo(effectiveTo) <= 0);
    }
}
//