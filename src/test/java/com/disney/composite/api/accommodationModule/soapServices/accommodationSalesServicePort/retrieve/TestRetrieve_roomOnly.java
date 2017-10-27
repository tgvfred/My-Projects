package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_roomOnly extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());

        isComo.set("false");
        bookReservation();
        System.out.println(getBook().getResponse());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_roomOnly() {

        Retrieve retrieve = new Retrieve(environment);
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setSiebelTravelPlanId("0");
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + retrieve.getFaultString(), retrieve);

        System.out.println(retrieve.getResponse());

        // clone validations
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
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

            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/response/partyRoles/role");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/response/couponProducts[text()='2789372']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/response/couponProducts[text()='2789375']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/response/couponProducts[text()='2789376']");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");

        }
    }

}
