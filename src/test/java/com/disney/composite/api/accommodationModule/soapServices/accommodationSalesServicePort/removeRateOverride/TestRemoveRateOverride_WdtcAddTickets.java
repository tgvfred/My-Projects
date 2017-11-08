package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.removeRateOverride;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.RemoveRateOverrideHelper;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;

public class TestRemoveRateOverride_WdtcAddTickets extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(Environment.getBaseEnvironmentName(environment));
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddTickets(true);
        isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "removeRateOverride", "accommodation", "accommodationsales" })
    public void testRemoveRateOverride_WdtcAddTickets() {
        RemoveRateOverrideHelper helper = new RemoveRateOverrideHelper();
        helper.validateCharge(environment, getBook());
    }
}