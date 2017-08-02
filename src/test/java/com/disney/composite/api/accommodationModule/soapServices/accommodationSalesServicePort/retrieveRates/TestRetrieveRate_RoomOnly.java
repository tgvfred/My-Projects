package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRate_RoomOnly extends AccommodationBaseTest {
    //private CheckInHelper helper;
     
    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
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
        String packageName = "R Room Only";
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
        TestReporter.assertEquals(retrieveRates.getPackageName(), packageName, "Validate the package name of '" + packageName + "' matches for tcgId " + tcgId);
        TestReporter.assertEquals(Randomness.generateCurrentXMLDate(), rateDate.split("T")[0], "Validate the Rate Date of '" + rateDate.split("T")[0]+ "' matches for tcgId '"+ tcgId +"'.");
        TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(retrieveRates.getRateDetails("1") !=null, "One Rate Details node is present");
        
        if (retrieveRates.getRateDetails("1") !=null){
        	TestReporter.log("One RateDetails node found");
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
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieveRates, true), "Validating Response Comparison");
        }
    }
}
