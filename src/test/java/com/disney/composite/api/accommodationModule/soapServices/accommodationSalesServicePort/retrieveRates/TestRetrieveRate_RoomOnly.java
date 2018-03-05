package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRate_RoomOnly extends AccommodationBaseTest {
    // private CheckInHelper helper;

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
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates" })
    public void TestRetrieveRates_roomOnly_oneNight() {
        String tcgId = getBook().getTravelComponentGroupingId();
        String tpId = getBook().getTravelPlanId();
        String roomCode = getRoomTypeCode();
        String packageName = "Room Only";
        String rateDate = "";

        TestReporter.logScenario("Retrieve Rates One Night");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);
        TestReporter.assertNotNull(retrieveRates.getRackRate(), "The response contains a rate");
        rateDate = retrieveRates.getRateDate("1");
        TestReporter.log("Travel Plan ID: " + tpId);
        TestReporter.assertEquals(retrieveRates.getroomTypeCode(), roomCode, "Verify that the room code matches '" + roomCode + "' for tcgId " + tcgId);
        TestReporter.assertTrue(retrieveRates.getPackageName().contains(packageName), "Validate the package name of '" + packageName + "' matches for tcgId " + tcgId);
        TestReporter.assertEquals(Randomness.generateCurrentXMLDate(), rateDate.split("T")[0], "Validate the Rate Date of '" + rateDate.split("T")[0] + "' matches for tcgId '" + tcgId + "'.");
        TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(retrieveRates.getRateDetails("1") != null, "One Rate Details node is present");

        if (retrieveRates.getRateDetails("1") != null) {
            TestReporter.log("One RateDetails node found");
        }

        validateRateDates(retrieveRates);

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
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieveRates, true), "Validating Response Comparison");
        }
    }

    private void validateRateDates(RetrieveRates retrieveRates) {
        TestReporter.logStep("Validating expected dates in the response");
        int numRateDetails = retrieveRates.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails");

        List<String> expectedDates = new ArrayList<>();
        expectedDates.add(Randomness.generateCurrentXMLDate());
        int currentIndexToRemove = 0;
        for (int i = 1; i <= numRateDetails; i++) {
            if (expectedDates.contains(retrieveRates.getRackRateDate(i).split("T")[0])) {
                expectedDates.remove(currentIndexToRemove);
                TestReporter.softAssertTrue(true, "The date [" + retrieveRates.getRackRateDate(i) + "] was found in a rate details node as expected.");
            } else {
                currentIndexToRemove++;
            }
        }
        if (expectedDates.size() > 0) {
            TestReporter.softAssertTrue(false, "Some dates were not found in the rate details as expected.");
            int numExtraneousDates = expectedDates.size();
            for (int i = 0; i < numExtraneousDates; i++) {
                TestReporter.softAssertTrue(false, "The date [" + expectedDates.get(i) + "] was not found in a rate details node as expected.");
            }
        } else {
            TestReporter.assertTrue(true, "All expected dates were found in the rate details as expected.");
        }

        TestReporter.logStep("Validating actual dates in the response");
        List<String> actualDates = new ArrayList<>();
        for (int i = 1; i <= numRateDetails; i++) {
            actualDates.add(retrieveRates.getRackRateDate(i).split("T")[0]);
        }
        expectedDates = new ArrayList<>();
        expectedDates.add(Randomness.generateCurrentXMLDate());
        Collections.sort(actualDates);
        Collections.sort(expectedDates);
        currentIndexToRemove = 0;
        if (!actualDates.containsAll(expectedDates)) {
            TestReporter.softAssertTrue(false, "The list of expected dates [" + expectedDates + "] does not match the list of actual dates [" + actualDates + "].");
        } else {
            TestReporter.softAssertTrue(true, "The list of expected dates [" + expectedDates + "] matches the list of actual dates [" + actualDates + "].");
        }

        TestReporter.assertAll();
    }
}
