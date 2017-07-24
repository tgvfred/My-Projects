package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_RetrieveSummary_oneTcg_roomOnly_Cancelled extends AccommodationBaseTest {

    private String cancel;

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        cancel();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnly_Cancelled() {

        cancel();
        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", retrieve);
        TestReporter.assertTrue(retrieve.getStatus().equals("Cancelled"), "Successfully cancelled! ");

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
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
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

        cancelCheck(getBook().getTravelComponentGroupingId());
    }

    public void cancelCheck(String tcgID) {

        String sql = "SELECT * FROM RES_MGMT.TC where TC_GRP_NB = " + tcgID;

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        cancel = rs.getValue("TRVL_STS_NM");

        TestReporter.assertEquals(cancel, "Cancelled", "Verify the reservation status [Cancelled] matches the status found"
                + " in the DB [" + cancel + "]");
    }
}