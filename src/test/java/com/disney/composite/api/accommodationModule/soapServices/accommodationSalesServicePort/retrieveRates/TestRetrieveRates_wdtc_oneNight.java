package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRates_wdtc_oneNight extends AccommodationBaseTest {

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        bookReservation();
        isComo.set("false");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates" })
    public void TestRetrieveRates_WDTC_oneNight() {
        String tcgId = getBook().getTravelComponentGroupingId();
        String tpId = getBook().getTravelPlanId();
        String roomCode = getRoomTypeCode();
        String rateDate = "";
        String billCode = "TRAVEL COMPANY PKGS";

        TestReporter.logScenario("Retrieve Rates One Night");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);
        rateDate = retrieveRates.getRateDate("1");
        TestReporter.log("Travel Plan ID: " + tpId);
        TestReporter.assertEquals(retrieveRates.getroomTypeCode(), roomCode, "Verify that the room code matches '" + roomCode + "' for tcgId " + tcgId);
        TestReporter.assertEquals(Randomness.generateCurrentXMLDate(), rateDate.split("T")[0], "Validate the Rate Date of '" + rateDate.split("T")[0] + "' matches for tcgId '" + tcgId + "'.");
        TestReporter.assertEquals(retrieveRates.getBillCode(), billCode, "Validate the package name of '" + billCode + "' matches for tcgId " + tcgId);
        TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(retrieveRates.getRateDetails("1") != null, "One rate details node is present ");

        if (retrieveRates.getRateDetails("1") != null) {
            TestReporter.log("Two rate detail nodes are found!");
        }

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveRates clone = (RetrieveRates) retrieveRates.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieveRates, true),
                    "Validating Response Comparison");
        }
    }
}
