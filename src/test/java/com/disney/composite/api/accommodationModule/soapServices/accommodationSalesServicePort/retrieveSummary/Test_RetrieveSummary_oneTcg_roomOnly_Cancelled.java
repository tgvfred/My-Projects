package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Cancel;
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

        Cancel cancel = new Cancel(environment);
        cancel.setCancelDate(getArrivalDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingIdIndexAdd("1", getBook().getTravelPlanSegmentId());
            retrieve.setRequestTravelComponentGroupingIdIndexAdd("2", getBook().getTravelComponentGroupingId());
            // retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);
        TestReporter.assertTrue(retrieve.getStatus().equals("Cancelled"), "Successfully cancelled! ");

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