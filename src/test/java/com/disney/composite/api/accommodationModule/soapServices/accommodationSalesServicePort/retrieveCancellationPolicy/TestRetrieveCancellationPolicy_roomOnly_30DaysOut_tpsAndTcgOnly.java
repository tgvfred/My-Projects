package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationPolicy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTravelPlanMediaCustomization;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy_roomOnly_30DaysOut_tpsAndTcgOnly extends AccommodationBaseTest {

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy" })
    public void testRetrieveCancellationPolicy_libgo_30DaysOut_fullRequest() {

        String cancelFee = "0.0";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred when sending request", retrieve);

        // Validate old vs. new service
        if (Environment.isSpecialEnvironment(getEnvironment())) {
            RetrieveTravelPlanMediaCustomization clone = (RetrieveTravelPlanMediaCustomization) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (retrieve.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }

            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/retrieveCancellationPolicy/request/travelPlanSegmentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveCancellationPolicy/request/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveCancellationPolicy/request/blockCode");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/retrieveCancellationPolicyResponse/cancellationPolicyResponse/cancelFee");
            TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true),
                    "Validating Response Comparison");
        }

        // Verify that cancel fee is 0.0
        TestReporter.logStep("Verify that the cancel fee is 0.0");
        TestReporter.assertTrue(retrieve.getcancelFee().equals(cancelFee), "Verify that cancel fee [" + retrieve.getcancelFee() + "] is zero");

        // Verify that fee waived node is not included in the response xml
        TestReporter.logStep("Verify that the fee waived node is not included in the response xml");
        try {
            retrieve.getFeeWaived();
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(true, "The fee waived node is not found in the response xml.");
        }

        // Verify that the policyText node is empty
        TestReporter.logStep("Verify that the policy text is empty");
        TestReporter.assertTrue(retrieve.getPolicyText().isEmpty(), "Verify that the policy text [" + retrieve.getPolicyText() + "] is empty.");
    }
}
