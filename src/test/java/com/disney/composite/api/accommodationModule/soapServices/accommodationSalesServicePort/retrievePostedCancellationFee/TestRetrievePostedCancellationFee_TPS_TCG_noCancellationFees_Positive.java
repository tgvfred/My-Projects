package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee_TPS_TCG_noCancellationFees_Positive extends AccommodationBaseTest {

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
    public void testRetrievePostedCancellationFee_TPS_TCG_noCancellationFees_Positive() {

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "TpsANDTcg");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setid(getBook().getTravelComponentGroupingId(), "2");
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

        TestReporter.softAssertEquals(retrieve.getWaived(), "false", "Verify the waived value is false as expected: [" + retrieve.getWaived() + "]");
        TestReporter.softAssertEquals(retrieve.getOverridden(), "false", "Verify the overridden value is false as expected: [" + retrieve.getOverridden() + "]");
        TestReporter.softAssertEquals(retrieve.getOverridePrice(), "0.0", "Verify the OverridePrice value is 0.0 as expected: [" + retrieve.getOverridePrice() + "]");
        TestReporter.softAssertEquals(retrieve.getProductPrice(), "0.0", "Verify the ProductPrice value is 0.0 as expected: [" + retrieve.getProductPrice() + "]");
        TestReporter.softAssertEquals(retrieve.getSellingPrice(), "0.0", "Verify the SellingPrice value is 0.0 as expected: [" + retrieve.getSellingPrice() + "]");

        TestReporter.assertAll();
    }
}
