package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveTravelPlanMediaCustomization;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTravelPlanMediaCustomization;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.ResortInfo;
import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveTravelPlanMediaCustomization_nonXbmsResort extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService",
            "retrieveTravelPlanMediaCustomization" })
    public void testRetrieveTravelPlanMediaCustomization_nonXbmsResort() {
        String travelPlanId = getBook().getTravelPlanId();
        String facilityId = "80010406";
        String resourceCode = "1U";
        String locationId = ResortInfo.getLocationID(ResortColumns.FACILITY_ID, getFacilityId());
        String expectedFacilityEligibility = "false";

        String sql = "select a.TXN_PTY_ID " + " from res_mgmt.tp_pty a " + " where a.tp_id = " + travelPlanId + " ";

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SkipException("No approved records found in recordset ");
        }

        RetrieveTravelPlanMediaCustomization retrieve = new RetrieveTravelPlanMediaCustomization(environment, "main");
        retrieve.setEnterpriseFacilityId(facilityId);
        retrieve.setTravelPlanId(rs.getValue("TXN_PTY_ID"));
        retrieve.setResortCode(resourceCode);
        retrieve.setLocationId(locationId);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"),
                "Error was returned: " + retrieve.getFaultString(), retrieve);

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
                    "/Envelope/Body/retrieveTravelPlanMediaCustomization/travelPlanId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveTravelPlanMediaCustomization/resortCode");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveTravelPlanMediaCustomization/locationId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/retrieveTravelPlanMediaCustomization/response/isFacilityEligible");
            TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true),
                    "Validating Response Comparison");
        }
        // validate that response xml is true
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"),
                "Error occurred when getting response code", retrieve);
        TestReporter.assertTrue(retrieve.getIsFacilityEligible().equals(expectedFacilityEligibility),
                "Verify that the isFacilityEligible [" + retrieve.getIsFacilityEligible()
                        + "] matches the expected value [" + expectedFacilityEligibility + "]");

        // validate that there are no guest experience media customization nodes
        // in the response xml
        try {
            retrieve.getResponseNodeValueByXPath(
                    "/Envelope/Body/retrieveTravelPlanMediaCustomizationResponse/response/experienceMediaDetails");
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(true, "No media customization nodes returned.");
        }
    }
}
