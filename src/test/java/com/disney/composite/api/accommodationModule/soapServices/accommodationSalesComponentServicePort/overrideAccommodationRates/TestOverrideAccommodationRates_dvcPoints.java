package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCPointsHelper;
import com.disney.utils.TestReporter;

public class TestOverrideAccommodationRates_dvcPoints extends BookDVCPointsHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setUseDvcResort(true);
        setUseNonZeroPoints(true);
        bookDvcReservation("testBookWithPay_MP", 1);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(getFirstBooking().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
        try {
            thawInventory(removeCM(environment), getInventoryTrackingId());
        } catch (Exception e) {
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative", "debug" })
    public void testOverrideAccommodationRates_dvcPoints() {
        String fault = "Rates for DVC points reservation cannot be overridden";

        TestReporter.logScenario("Test - Override Accommodation Rates - dvcPoints");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        oar.setTpsID(getFirstBooking().getTravelPlanSegmentId());
        oar.setTcgId(getFirstBooking().getTravelComponentGroupingId());
        oar.setDate(getArrivalDate());
        oar.setRackRateDate(getArrivalDate());

        oar.sendRequest();
        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.RATE_OVERRIDE_FAILURE);
    }
}