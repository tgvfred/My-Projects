package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveComments_invalidParentId extends AccommodationBaseTest {

    private static Database db;
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetriveComments", "negative" })
    public void TestRetrieveComments_invalidParentId() {
        db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select * from "
                + "( "
                + "select unique(c.tc_id) "
                + "from res_mgmt.tc c "
                + "where c.tc_id not in( "
                + "        select unique(a.tc_id) "
                + "        from res_mgmt.tc a "
                + "        join res_mgmt.res_mgmt_req b on a.tc_id = b.tc_id "
                + "        where a.tc_typ_nm ='AccommodationComponent' "
                + ") "
                + ") "
                + "where ROWNUM < 2 ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String tcID = rs.getValue("TC_ID", 1);

        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(tcID);
        retrieve.sendRequest();
        TestReporter.logAPI(retrieve.getResponseStatusCode().equals("200"), "An error occurred getting comment as expected", retrieve);

        String errorXPath = "/Envelope/Body/Fault/";

        TestReporter.softAssertEquals(true, retrieve.getResponseNodeValueByXPath(errorXPath + "faultstring").contains("No Accommodation Component found. : No Accommodation Found with id#" + tcID), "Validate that" + " error contains No Accommodation Component found. : No Accommodation Found with id#" + tcID);

    }

    private void validateNoInactive(RetrieveComments retrieve) {
        TestReporter.logStep("Validate Active Comments Come First");
        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";

            TestReporter.softAssertEquals(false, retrieve.getResponseNodeValueByXPath(commentXPath + "commentText").contains("late checkout"), "Validate that no late checkout comments are returned");
            break;
        }
        TestReporter.assertAll();
    }
}
