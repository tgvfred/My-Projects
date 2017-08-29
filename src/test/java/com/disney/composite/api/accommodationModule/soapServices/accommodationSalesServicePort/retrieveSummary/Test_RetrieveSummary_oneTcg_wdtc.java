package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_wdtc extends AccommodationBaseTest {

    private String environment;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        isComo.set("false");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_wdtc() {

        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        bookReservation();

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify roomOnly node is false.");
        TestReporter.assertTrue(retrieve.getRoomOnlyStatus().equals("false"), "Room Only node returns false! ");

    }

}
