package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRates_DVC_Cash extends BookDVCCashHelper {
    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        setUseDvcResort(true);
        setBook(bookDvcReservation("testBook_MCash", 1));
        setTpId(getFirstBooking().getTravelPlanId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveRates" })
    public void TestRetrieveRates_dvcCashReservation() {
        String tcgId = getBook().getTravelComponentGroupingId();
        String tpId = getBook().getTravelPlanId();
        String roomCode = getRoomTypeCode();
        String rateDate = "";
        String billCode = "Group Pays No Charges";
        String packageName = "WDW DVC Member Room Only";
        
        TestReporter.logScenario("Retieve DVC Cash");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getFirstBooking().getTravelComponentGroupingId() + "]", retrieveRates);
        rateDate = retrieveRates.getRateDate("1");
        TestReporter.log("Travel Plan ID: " + tpId);
        TestReporter.assertEquals(retrieveRates.getroomTypeCode(), roomCode, "Verify that the room code matches '" + roomCode + "' for tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getPackageName(), packageName, "Validate the package name of '" + packageName + "' matches for tcgId " + tcgId);
        TestReporter.assertEquals(retrieveRates.getBillCode(), billCode, "Validate the bill code  '" + billCode + "' matches for tcgId " + tcgId);
        TestReporter.assertEquals(getArrivalDate(), rateDate.split("T")[0], "Validate the Rate Date of '" + rateDate.split("T")[0]+ "' matches for tcgId '"+ tcgId +"'.");
        TestReporter.logStep("Verify number of nodes being returned");
        TestReporter.assertTrue(retrieveRates.getRateDetails("1") !=null, "One Rate Details node is present");
        
        if (retrieveRates.getRateDetails("1") !=null){
        	TestReporter.log("One RateDetails node found");
        }
        
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
