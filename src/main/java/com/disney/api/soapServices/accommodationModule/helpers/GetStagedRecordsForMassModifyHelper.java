package com.disney.api.soapServices.accommodationModule.helpers;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.AutomationException;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class GetStagedRecordsForMassModifyHelper {
    public static String sql;
    public static Database db;
    public static Recordset rs;
    public AccommodationBaseTest base;
    public String environment;

    public GetStagedRecordsForMassModifyHelper(String environment, AccommodationBaseTest base) {
        if (isValid(environment)) {
            this.environment = environment;
        } else {
            throw new AutomationException("The environment field cannot be null or empty");
        }
        if (isValid(base)) {
            this.base = base;
        } else {
            throw new AutomationException("The AccommodationBaseTest object cannot be null or empty");
        }
    }

    public void validateNumberOfTcs(int numRecords) {
        TestReporter.logStep("Validate the number of TCs");
        sql = "select count(c.TC_ID) "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = " + base.getBook().getTravelPlanSegmentId();
        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertEquals(rs.getRowCount(), numRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numRecords + "]");
    }

    public void validateReservationDetails(String agencyPartyId, String agentPartyId, String securityValue, String guarIndicator, String arrivalDate, String departureDate, String assignmentOwner, boolean assignOwnerChange) {
        TestReporter.logStep("Validate the DB reservtaion details");
        sql = "select c.TC_TYP_NM, a.TRVL_AGCY_PTY_ID, a.TRVL_AGT_PTY_ID, a.TPS_SECUR_VL, a.TPS_GUAR_IN, a.TPS_ARVL_DT, a.TPS_DPRT_DT, c.TC_STRT_DTS, c.TC_END_DTS, c.ASGN_OWN_ID "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tps_id = " + base.getBook().getTravelPlanSegmentId();
        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        do {
            TestReporter.softAssertEquals(rs.getValue("TRVL_AGCY_PTY_ID"), agencyPartyId, "Verify that the agency party ID [" + rs.getValue("TRVL_AGCY_PTY_ID") + "] is that which is expected [" + agencyPartyId + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_AGT_PTY_ID"), agentPartyId, "Verify that the agent party ID [" + rs.getValue("TRVL_AGT_PTY_ID") + "] is that which is expected [" + agentPartyId + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_SECUR_VL"), securityValue, "Verify that the security value [" + rs.getValue("TPS_SECUR_VL") + "] is that which is expected [" + securityValue + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_GUAR_IN"), guarIndicator, "Verify that the guarantee indicator [" + rs.getValue("TPS_GUAR_IN") + "] is that which is expected [" + guarIndicator + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT").split(" ")[0], arrivalDate, "Verify that the tps arrival date [" + rs.getValue("TPS_ARVL_DT").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT").split(" ")[0], departureDate, "Verify that the tps departure date [" + rs.getValue("TPS_DPRT_DT").split(" ")[0] + "] is that which is expected [" + departureDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_STRT_DTS").split(" ")[0], arrivalDate, "Verify that the tc arrival date [" + rs.getValue("TC_STRT_DTS").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TC_END_DTS").split(" ")[0], departureDate, "Verify that the tc departure date [" + rs.getValue("TC_END_DTS").split(" ")[0] + "] is that which is expected [" + departureDate + "].");
            if (rs.getValue("TC_TYP_NM").equals("AccommodationComponent")) {
                if (assignOwnerChange) {
                    TestReporter.softAssertTrue(!rs.getValue("ASGN_OWN_ID").equals(assignmentOwner), "Verify that the [" + rs.getValue("ASGN_OWN_ID") + "] is not the previous value [" + assignmentOwner + "].");
                } else {
                    TestReporter.softAssertTrue(rs.getValue("ASGN_OWN_ID").equals(assignmentOwner), "Verify that the [" + rs.getValue("ASGN_OWN_ID") + "] is the previous value [" + assignmentOwner + "].");
                }
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }
}