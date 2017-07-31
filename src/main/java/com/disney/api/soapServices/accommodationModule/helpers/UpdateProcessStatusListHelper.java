package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.Environment;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class UpdateProcessStatusListHelper {

    private ThreadLocal<String> environment = new ThreadLocal<String>();
    private String multID;
    private String mult;
    private String rlID;
    private String rl;
    private String runID;
    private String id;
    private String resID;
    private String stsNM;
    private String idCD;
    private String date;

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

        runID = rs.getValue("GRP_RES_PROC_RUN_ID");
        this.id = runID;

        return runID;
    }

    // Validations

    public void validationOverall(String procRunId, String status) {

        String sql = "select a.GRP_RES_PROC_RUN_ID, a.GRP_RES_PROC_RUN_STS_NM, a.UPDT_USR_ID_CD, a.UPDT_DTS "
                + "from res_mgmt.GRP_RES_PROC_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        stsNM = rs.getValue("GRP_RES_PROC_RUN_STS_NM");
        idCD = rs.getValue("UPDT_USR_ID_CD");
        date = rs.getValue("UPDT_DTS");

        TestReporter.assertEquals(stsNM, status, "Verify the Status Name [" + status + "] matches the Status Name found"
                + " in the DB [" + stsNM + "]");

        TestReporter.assertEquals(idCD, "AutoJUnit.us", "Verify the ID Code [AutoJUnit.us] matches the ID Code found"
                + " in the DB [" + idCD + "]");

        // Because the date/time will always change a Regex validation is required
        TestReporter.assertTrue(Regex.match("[a-z A-Z 0-9].*", date), "Verify the Update Date [" + date + "] matches the Update Date found in the DB [" + date + "]");

    }

    public void validationResID(String procRunId, String test) {

        String sql = "select a.GRP_RES_PROC_RUN_ID, a.RES_ID "
                + "from res_mgmt.RM_LIST_RES_RUN a "
                + "where a.GRP_RES_PROC_RUN_ID = '" + procRunId + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        resID = rs.getValue("RES_ID");

        TestReporter.assertEquals(resID, test, "Verify the Reservation ID [" + test + "] matches the Rservation ID found"
                + " in the DB [" + resID + "]");

    }
}
