package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePackageAndRackRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePackageAndRackRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrievePackageAndRackRates_wholesaler extends AccommodationBaseTest {
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
        setIsLibgoBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

    }

    // test
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates" })
    public void testRetrievePackageAndRackRates_wholesaler() {
        RetrievePackageAndRackRates retrievePackage = new RetrievePackageAndRackRates(environment, "Main");
        retrievePackage.setaccomComponentId(getBook().getTravelComponentId());
        retrievePackage.setPackageCode(getPackageCode());
        retrievePackage.setTravelPlanSegementId(getBook().getTravelPlanSegmentId());
        retrievePackage.sendRequest();

        TestReporter.logAPI(!retrievePackage.getResponseStatusCode().equals("200"), "An error occurred retrieving the package and rack rates [" + getBook().getTravelComponentGroupingId() + "]", retrievePackage);

        // validations
        retrievePackage.validateResponseNodeQuantity("retrievePackageAndRackRatesResponse", true);
        //clone validation
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            RetrievePackageAndRackRates clone = (RetrievePackageAndRackRates) retrievePackage.clone();
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
                if (retrievePackage.getResponseStatusCode().equals("200")) {
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

            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrievePackageAndRackRatesResponse/response/partyRoles/role");

            clone.addExcludedXpathValidations("/Envelope/Body/retrievePackageAndRackRatesResponse/response/couponProducts[text()='2789372']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrievePackageAndRackRatesResponse/response/couponProducts[text()='2789375']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrievePackageAndRackRatesResponse/response/couponProducts[text()='2789376']");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrievePackage, true), "Validating Response Comparison");

        }
    }
}
