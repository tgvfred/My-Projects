package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveComments_roomOnly_lateCheckoutComment extends AccommodationBaseTest {

    private static Database db;

    @DataProvider(name = "TCID")
    public Object[][] data() {
        db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select TC_ID from "
                + "( "
                + "select * "
                + "from ( "
                + "select a.tc_id, count(a.TC_ID) tc_count "
                + "from res_mgmt.tc a "
                + "join res_mgmt.res_mgmt_req b on a.tc_id = b.tc_id "
                + "where a.tc_typ_nm ='AccommodationComponent' "
                + "and CMT_REQ_TYP_NM is not null "
                + "and b.CMT_REQ_TYP_NM = 'LateCheckOut' "
                + "group by a.TC_ID "
                + ") "
                + "having tc_count > 1 "
                + ") "
                + "where ROWNUM < 2";
        return db.getResultSetAsDataProvider(sql);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetriveComments" })
    public void TestRetrieveComments_roomOnly_lateCheckoutComment_validateLateCommentsExist_positive() {
        db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select TC_ID from "
                + "( "
                + "select * "
                + "from ( "
                + "select a.tc_id, count(a.TC_ID) tc_count "
                + "from res_mgmt.tc a "
                + "join res_mgmt.res_mgmt_req b on a.tc_id = b.tc_id "
                + "where a.tc_typ_nm ='AccommodationComponent' "
                + "and CMT_REQ_TYP_NM is not null "
                + "and b.CMT_REQ_TYP_NM = 'LateCheckOut' "
                + "group by a.TC_ID "
                + ") "
                + "having tc_count > 1 "
                + ") ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            TestReporter.assertTrue(rs.getRowCount() == 0, "No Late Checkout Comments Found, second test will not run");
        } else {
            TestReporter.assertTrue(rs.getRowCount() != 0, "Late Checkout Comments Found");
        }

    }

    @Test(dataProvider = "TCID", groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetriveComments" })
    public void TestRetrieveComments_roomOnly_lateCheckoutComment_positive(String tcID) {
        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(tcID);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
        validateNoInactive(retrieve);

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
