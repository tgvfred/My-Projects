package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class UpdateProcessStatusListHelper {

    private ThreadLocal<String> environment = new ThreadLocal<String>();
    private String multID;
    private String mult;
    private String rlID;
    private String rl;
    private String runID;
    private String id;
    private String cnclID;
    private String stsNM;
    private String idCD;
    private String date;
    private String date1;
    private String tpID;
    private String tcID;
    private String tpsID;
    private String tcgID;
    private String cntNM;
    private String cnclRS;
    private String grpID;
    private String runID1;
    private String runID2;
    private String resID;

    public UpdateProcessStatusListHelper(String environment) {
        this.environment.set(environment);
    }

    public String getEnvironment() {
        return this.environment.get();
    }

    public String multipleID() {

        String sql = "select c.GRP_RES_PROC_ID, c.GRP_RES_PROC_TYP_NM, d.GRP_RES_PROC_RUN_ID "
                + "from res_mgmt.GRP_RES_PROC c "
                + "join res_mgmt.GRP_RES_PROC_RUN d on c.GRP_RES_PROC_ID = d.GRP_RES_PROC_ID "
                + "where c.GRP_RES_PROC_ID = (select GRP_RES_PROC_ID "
                + "from (select a.GRP_RES_PROC_ID, count(a.GRP_RES_PROC_ID) "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where b.GRP_RES_PROC_RUN_STS_NM = 'SUBMITTED' "
                + "and b.UPDT_DTS <=  to_date('2017-06-17','YYYY-MM-DD:HH24:MI:SS') "
                + "group by a.GRP_RES_PROC_ID "
                + "having count(a.GRP_RES_PROC_ID) > 1 "
                + "order by dbms_random.value) "
                + "where rownum = 1)";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No multiple IDs found", sql);
        }

        multID = rs.getValue("GRP_RES_PROC_ID");
        this.mult = multID;

        return multID;

    }

    public String roomListID() {

        String sql = "select c.GRP_RES_PROC_ID, c.GRP_RES_PROC_TYP_NM, d.GRP_RES_PROC_RUN_ID "
                + "from res_mgmt.GRP_RES_PROC c "
                + "join res_mgmt.GRP_RES_PROC_RUN d on c.GRP_RES_PROC_ID = d.GRP_RES_PROC_ID "
                + "where c.GRP_RES_PROC_ID IN (select GRP_RES_PROC_ID "
                + "from (select unique(a.GRP_RES_PROC_ID) "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where b.GRP_RES_PROC_RUN_STS_NM = 'SUBMITTED' "
                + "and b.UPDT_DTS <=  to_date('2017-06-17','YYYY-MM-DD:HH24:MI:SS') "
                + "and a.GRP_RES_PROC_TYP_NM = 'ROOMINGLIST' "
                + "order by dbms_random.value) "
                + "where rownum = 1)";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No room list ID found", sql);
        }

        rlID = rs.getValue("GRP_RES_PROC_ID");
        this.rl = rlID;

        return rlID;

    }

    public String retrieveProcRunId(String processId) {

        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where a.GRP_RES_PROC_ID in '" + processId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for process ID [ " + processId + " ]", sql);
        }

        runID = rs.getValue("GRP_RES_PROC_RUN_ID");
        this.id = runID;

        return runID;
    }

    public String retrieveProcRunIdMulti1(String processId) {

        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where a.GRP_RES_PROC_ID in '" + processId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for process ID [ " + processId + " ]", sql);
        }

        runID1 = rs.getValue("GRP_RES_PROC_RUN_ID", 1);
        this.id = runID1;

        return runID1;
    }

    public String retrieveProcRunIdMulti2(String processId) {

        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where a.GRP_RES_PROC_ID in '" + processId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for process ID [ " + processId + " ]", sql);
        }

        runID2 = rs.getValue("GRP_RES_PROC_RUN_ID", 2);
        this.id = runID2;

        return runID2;
    }

    // Validations

    public void validationOverall(String procRunId, String status, String procDate) {
        Sleeper.sleep(10000);
        String sql = "select a.GRP_RES_PROC_RUN_ID, a.GRP_RES_PROC_RUN_STS_NM, a.UPDT_USR_ID_CD, a.GRP_RES_PROC_RUN_DTS "
                + "from res_mgmt.GRP_RES_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        stsNM = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
        idCD = rs.getValue("UPDT_USR_ID_CD");
        date = rs.getValue("GRP_RES_PROC_RUN_DTS");

        TestReporter.logStep("Validate proc run id [" + procRunId + "].");

        TestReporter.assertEquals(stsNM, status, "Verify the Status Name [" + status + "] matches the Status Name found"
                + " in the DB [" + stsNM + "]");

        TestReporter.assertEquals(idCD, "AutoJUnit.us", "Verify the ID Code [AutoJUnit.us] matches the ID Code found"
                + " in the DB [" + idCD + "]");

        TestReporter.assertEquals(date.substring(0, 10), procDate, "Verify the Group Reservation Process Run Date [" + procDate + "] matches the Group Reservation Process Run Date found"
                + " in the DB [" + date.substring(0, 10) + "]");
    }

    public void validationOverallRest(String procRunId, String status, String code, String procDate) {
        Sleeper.sleep(10000);
        String sql = "select a.GRP_RES_PROC_RUN_ID, a.GRP_RES_PROC_RUN_STS_NM, a.UPDT_USR_ID_CD, a.GRP_RES_PROC_RUN_DTS "
                + "from res_mgmt.GRP_RES_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        stsNM = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
        idCD = rs.getValue("UPDT_USR_ID_CD");
        date = rs.getValue("GRP_RES_PROC_RUN_DTS");

        TestReporter.logStep("Validate proc run id [" + procRunId + "].");

        TestReporter.assertEquals(stsNM, status, "Verify the Status Name [" + status + "] matches the Status Name found"
                + " in the DB [" + stsNM + "]");

        TestReporter.assertEquals(idCD, code, "Verify the ID Code [" + code + "] matches the ID Code found"
                + " in the DB [" + idCD + "]");

        TestReporter.assertEquals(date.substring(0, 10), procDate, "Verify the Group Reservation Process Run Date [" + procDate + "] matches the Group Reservation Process Run Date found"
                + " in the DB [" + date.substring(0, 10) + "]");
    }

    public void validationOverall_MASSCancel(String procRunId, String status, String procDate) {
        Sleeper.sleep(10000);
        String sql = "select a.GRP_RES_PROC_RUN_ID, a.GRP_RES_PROC_RUN_STS_NM, a.UPDT_USR_ID_CD, a.GRP_RES_PROC_RUN_DTS "
                + "from res_mgmt.GRP_RES_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        stsNM = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
        idCD = rs.getValue("UPDT_USR_ID_CD");
        date = rs.getValue("GRP_RES_PROC_RUN_DTS");

        TestReporter.logStep("Validate proc run id [" + procRunId + "].");

        TestReporter.assertEquals(stsNM, status, "Verify the Status Name [" + status + "] matches the Status Name found"
                + " in the DB [" + stsNM + "]");

        TestReporter.assertEquals(idCD, "MASSCancel", "Verify the ID Code [MASSCancel] matches the ID Code found"
                + " in the DB [" + idCD + "]");

        TestReporter.assertEquals(date.substring(0, 10), procDate, "Verify the Group Reservation Process Run Date [" + procDate + "] matches the Group Reservation Process Run Date found"
                + " in the DB [" + date.substring(0, 10) + "]");
    }

    public void validationMassCancel(String procRunId, String tpid, String tcgid) {

        String sql = "select * "
                + "from res_mgmt.ACM_CNCL_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        cnclID = rs.getValue("ACM_CNCL_PROC_RUN_ID");
        tpID = rs.getValue("TP_ID");
        tcgID = rs.getValue("TC_GRP_NB");
        cntNM = rs.getValue("CNCL_CNTCT_NM");
        cnclRS = rs.getValue("CNCL_RSN_TX");

        TestReporter.assertTrue(!(cnclID.equals(null)), "Accommodation Cancel Process Run ID is found! [" + cnclID + "]");

        TestReporter.assertEquals(tpID, tpid, "Verify the Travel Plan ID [" + tpid + "] matches the Travel Plan ID found"
                + " in the DB [" + tpID + "]");

        TestReporter.assertEquals(tcgID, tcgid, "Verify the Travel Component Grouping ID [" + tcgid + "] matches the Travel Component Grouping ID found"
                + " in the DB [" + tcgID + "]");

        TestReporter.assertEquals(cntNM, "Cancel Name", "Verify the Cancel Contact Name [Cancel Name] matches the Cancel Contact Name found"
                + " in the DB [" + cntNM + "]");

        TestReporter.assertEquals(cnclRS, "AIR", "Verify the Cancel Reason [AIR] matches the Cancel Reason found"
                + " in the DB [" + cnclRS + "]");
    }

    public void validationMassModify(String procRunId, String tpsid, String tcgid, String tcid) {

        String sql = "select * "
                + "from res_mgmt.GRP_RES_MOD_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        cnclID = rs.getValue("GRP_RES_MOD_RUN_ID");
        tpsID = rs.getValue("TPS_ID");
        tcgID = rs.getValue("TC_GRP_NB");
        tcID = rs.getValue("TC_ID");

        TestReporter.assertTrue(!(cnclID.equals(null)), "Group Reservation Modify Run ID is found! [" + cnclID + "]");

        TestReporter.assertEquals(tpsID, tpsid, "Verify the Travel Plan Segment ID [" + tpsid + "] matches the Travel Plan Segment ID found"
                + " in the DB [" + tpsID + "]");

        TestReporter.assertEquals(tcgID, tcgid, "Verify the Travel Component Grouping ID [" + tcgid + "] matches the Travel Component Grouping ID found"
                + " in the DB [" + tcgID + "]");

        TestReporter.assertEquals(tcID, tcid, "Verify the Travel Component ID [" + tcid + "] matches the Travel Component ID found"
                + " in the DB [" + tcID + "]");
    }

    public void validationRemoveGroup(String procRunId, String tcgid) {

        String sql = "select * "
                + "from res_mgmt.RMVE_GRP_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        grpID = rs.getValue("RMVE_GRP_PROC_RUN_ID");
        tcgID = rs.getValue("TC_GRP_NB");

        TestReporter.assertTrue(!(grpID.equals(null)), "Remove Group Process Run ID is found! [" + grpID + "]");

        TestReporter.assertEquals(tcgID, tcgid, "Verify the Travel Component Grouping ID [" + tcgid + "] matches the Travel Component Grouping ID found"
                + " in the DB [" + tcgID + "]");

    }

    public void validationRoomList(String procRunId, String res) {

        String sql = "select * "
                + "from res_mgmt.RM_LIST_RES_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for proc run ID [ " + procRunId + " ]", sql);
        }

        grpID = rs.getValue("GRP_RES_PROC_RUN_ID");
        resID = rs.getValue("RES_ID");
        idCD = rs.getValue("CREATE_USR_ID_CD");

        TestReporter.assertTrue(!(grpID.equals(null)), "Group Reservation Process Run ID is found! [" + grpID + "]");

        TestReporter.assertEquals(resID, res, "Verify the Reservation ID [" + res + "] matches the Reservation ID found"
                + " in the DB [" + resID + "]");

        TestReporter.assertEquals(idCD, "AutoJUnit.us", "Verify the ID Code [AutoJUnit.us] matches the ID Code found"
                + " in the DB [" + idCD + "]");
    }

}
