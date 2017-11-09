package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.removeRateOverride;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.RemoveRateOverrideHelper;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class TestRemoveRateOverride_RoomOnlyMultipleGuests extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setAddNewGuest(true);
        isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "removeRateOverride", "accommodation", "accommodationsales" })
    public void testRemoveRateOverride_RoomOnlyMultipleGuests() {
        RemoveRateOverrideHelper helper = new RemoveRateOverrideHelper();
        helper.validateCharge(environment, getBook());
    }
}