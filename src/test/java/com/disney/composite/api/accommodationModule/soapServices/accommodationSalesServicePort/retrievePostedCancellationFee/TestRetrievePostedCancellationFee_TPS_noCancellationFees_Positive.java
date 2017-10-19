package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee_TPS_noCancellationFees_Positive extends AccommodationBaseTest {

    private CheckInHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_noCancellationFees_Positive() {

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "Main");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

        TestReporter.softAssertEquals(retrieve.getWaived(), "false", "Verify the waived value is false as expected: [" + retrieve.getWaived() + "]");
        TestReporter.softAssertEquals(retrieve.getOverridden(), "false", "Verify the overridden value is false as expected: [" + retrieve.getOverridden() + "]");
        TestReporter.assertAll();
    }
}
