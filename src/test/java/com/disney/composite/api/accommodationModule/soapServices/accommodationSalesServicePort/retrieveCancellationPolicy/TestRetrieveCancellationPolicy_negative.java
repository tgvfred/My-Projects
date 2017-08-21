package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationPolicy;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningBatchModule.diningBatchService.operations.AutoCancel;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveCancellationPolicy_negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullRequest() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request", BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullTcg() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_negativeTcg() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("-1");
        retrieve.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullTps() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_negativeTps() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("-1");
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_invalidTps() {

        String faultString = "Travel Plan Segment Should not be NULL";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("5555555");
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.TRVL_PLAN_SGMT_CANNOT_BE_NULL);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_invalidTcg() {

        String faultString = "Accommodations not found";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId("555555");
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_autoCancelled() {

        String faultString = "Cannot calculate Cancel fee";

        AutoCancel autoCancel = new AutoCancel(getEnvironment(), "Main");
        autoCancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        autoCancel.sendRequest();

        String sql = "select tps_id, tc_grp_nb " +
                " from( " +
                " select b.tps_id, b.tc_grp_nb " +
                " from res_mgmt.tps a " +
                " join res_mgmt.tc_grp b on a.tps_id = b.tps_id " +
                " where a.trvl_sts_nm = 'Auto Cancelled' " +
                " order by dbms_random.value " +
                " ) " +
                " where rownum = 1";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        System.out.println("Auto Cancel");
        rs.print();

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_cancelled() {

        String faultString = "Cannot calculate Cancel fee";

        String sql = "select tps_id, tc_grp_nb " +
                " from( " +
                " select b.tps_id, b.tc_grp_nb " +
                " from res_mgmt.tps a " +
                " join res_mgmt.tc_grp b on a.tps_id = b.tps_id " +
                " where a.trvl_sts_nm = 'Cancelled' " +
                " order by dbms_random.value " +
                " ) " +
                " where rownum = 1";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(rs.getValue("TPS_ID"));
        retrieve.setTravelComponentGroupingId(rs.getValue("TC_GRP_NB"));
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_checkedIn() {
        String faultString = "Cannot calculate Cancel fee";

        String sql = "select tps_id, tc_grp_nb " +
                " from( " +
                " select b.tps_id, b.tc_grp_nb " +
                " from res_mgmt.tps a " +
                " join res_mgmt.tc_grp b on a.tps_id = b.tps_id " +
                " where a.trvl_sts_nm = 'Checked In' " +
                " order by dbms_random.value " +
                " ) " +
                " where rownum = 1";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(rs.getValue("TPS_ID"));
        retrieve.setTravelComponentGroupingId(rs.getValue("TC_GRP_NB"));
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_checkedOut() {
        String faultString = "Cannot calculate Cancel fee";

        String sql = "select tps_id, tc_grp_nb " +
                " from( " +
                " select b.tps_id, b.tc_grp_nb " +
                " from res_mgmt.tps a " +
                " join res_mgmt.tc_grp b on a.tps_id = b.tps_id " +
                " where a.trvl_sts_nm = 'Checked Out' " +
                " order by dbms_random.value " +
                " ) " +
                " where rownum = 1";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(rs.getValue("TPS_ID"));
        retrieve.setTravelComponentGroupingId(rs.getValue("TC_GRP_NB"));
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }
}
