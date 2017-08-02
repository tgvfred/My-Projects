package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCPointsHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRates_DVC_Points extends BookDVCPointsHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        
        setUseDvcResort(true);
        setUseNonZeroPoints(true);
        setBook(bookDvcReservation("testBookWithPay_MP", 1));
        setTpId(getFirstBooking().getTravelPlanId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveRates" })
    public void TestRetrieveRates_dvcPointsReservation() {
    	//public static String toString(int i)
        String tcgId = getBook().getTravelComponentGroupingId();
        String tpId = getBook().getTravelPlanId();
        String roomCode = getRoomTypeCode();
        String packageName = "WDW DVC Member Points Room Only";
        String rateDate = "";
        String dayCount = Integer.toString(getNights());
        String overridden = "true";
        String shared = "false";
        String rackRateDate = "";
        String pointsValue = "1";
        
        
        
        
        TestReporter.logScenario("Retieve DVC Points");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getFirstBooking().getTravelComponentGroupingId() + "]", retrieveRates);
        rateDate = retrieveRates.getRateDate();
        rackRateDate = retrieveRates.getRackRateDate();
        //pointsValue = retrieveRates.getPointsValue();
        
        TestReporter.log("Travel Plan ID: " + tpId);
        TestReporter.assertEquals(retrieveRates.getroomTypeCode(), roomCode, "Verify that the room code matches '" + roomCode + "' for tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getPackageName(), packageName, "Verify the package name of '" + packageName + "' matches for tcgId " + tcgId);
        TestReporter.assertEquals(getArrivalDate(), rateDate.split("T")[0], "Verify the rate details date of " + getArrivalDate().split("T")[0] + " match with tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getDayCount(), dayCount, "Verify the days count match [ " + dayCount +" ] for tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getRateDetailsOveridden(), overridden, "verify the status for overridden is " + overridden + " for tcgId" + tcgId );
        TestReporter.assertEquals(retrieveRates.getShared(), shared, "verify the status is " + shared + "f or tcgId" + tcgId);
        TestReporter.assertEquals(getArrivalDate(), rackRateDate.split("T")[0], "Verify the rate details date of " + getArrivalDate().split("T")[0] + " match with tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getPointsValue(), pointsValue, "Verify the points value of '" + pointsValue + "' match for tcgId " + tcgId);
        
        // Old vs New Validation
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