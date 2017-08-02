package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestRetrieveRate_RoomOnly_CheckIn extends AccommodationBaseTest {
    private CheckInHelper helper;
     
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
    public void TestRetrieveRates_roomOnly_checkedIn() {
    	String tcgId = getBook().getTravelComponentGroupingId();
        String tpId = getBook().getTravelPlanId();
        String roomCode = getRoomTypeCode();
        String packageName = "R Room Only";

        TestReporter.logScenario("Book and Check In");
        helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        TestReporter.logScenario("Retrieve rates");
        RetrieveRates RetrieveRates = new RetrieveRates(environment, "retrieveRates");
        RetrieveRates.setTravelComponentGroupingId(tcgId);
        RetrieveRates.sendRequest();
        TestReporter.logAPI(!RetrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", RetrieveRates);
        TestReporter.assertNotNull(RetrieveRates.getRackRate(), "The response contains a rate");
        TestReporter.log("Travel Plan ID: " + tpId);
        TestReporter.assertEquals(RetrieveRates.getroomTypeCode(), roomCode, "Verify that the room code matches '" + roomCode + "' for tcgId " + tcgId);
        TestReporter.assertEquals(RetrieveRates.getPackageName(), packageName, "Validate the package name of '" + packageName + "' matches for tcgId " + tcgId);
        TestReporter.assertNotNull(RetrieveRates.getRateDate(), "Validate the Rate Date is present for tcgId '"+ tcgId +"'.");
        TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(RetrieveRates.getRateDetails("1") !=null, "One Rate Details node is present");
        
        if (RetrieveRates.getRateDetails("1") !=null){
        	TestReporter.log("One RateDetails node found");
        }
        
        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveRates clone = (RetrieveRates) RetrieveRates.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(RetrieveRates, true),
                    "Validating Response Comparison");
        }
    }

}
