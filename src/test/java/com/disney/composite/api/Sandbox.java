package com.disney.composite.api;

import java.util.Date;

import org.testng.annotations.Test;

import com.disney.api.helpers.Agency;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        Agency agency = new Agency("Latest");
        System.out.println(agency.getAgencyId());
        System.out.println(agency.getOrganizationId());

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