package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveTeamByGroupCode;

import java.util.List;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTeamsByGroupCode;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveTeamByGroupCode extends BaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode" })
    public void testRetrieveTeamsByGroupCodeMissingGroupCode() {

        RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment);
        // Skip setting group codes.
        retrieveTeamsByGroupCode.sendRequest();
        TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was  200", retrieveTeamsByGroupCode);
        TestReporter.assertNull(retrieveTeamsByGroupCode.getTeamCode(), "The team code retrieved: " + retrieveTeamsByGroupCode.getTeamCode() + ".   Expected null");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode" })
    public void testRetrieveTeamsByGroupCodeSingle() {

        String sql = "select a.grp_cd, a.grp_tm_nm " +
                "from res_mgmt.grp_tm a " +
                "where a.grp_cd in ( " +
                "select * " +
                "from (select a.grp_cd " +
                "from res_mgmt.grp_tm a " +
                "group by a.grp_cd " +
                "having count(a.grp_tm_nm) < 2 " +
                "order by dbms_random.value )  " +
                "where rownum < 2 ) ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String groupCode = rs.getValue("grp_cd");
        String teamCode = rs.getValue("grp_tm_nm");

        RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment);
        retrieveTeamsByGroupCode.setgroupcode(groupCode);
        retrieveTeamsByGroupCode.sendRequest();
        TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was 200", retrieveTeamsByGroupCode);

        String teamCodeRetrieved = retrieveTeamsByGroupCode.getTeamCode();

        TestReporter.assertEquals(teamCode, teamCodeRetrieved, "The team code retrieved: " + teamCode + " does matches teamCode in db: " + teamCodeRetrieved);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode" })
    public void testRetrieveTeamsByGroupCodeMultiple() {

        String sql = "select a.grp_cd, a.grp_tm_nm " +
                "from res_mgmt.grp_tm a " +
                "where a.grp_cd in ( " +
                "select * " +
                "from (select a.grp_cd " +
                "from res_mgmt.grp_tm a " +
                "group by a.grp_cd " +
                "having count(a.grp_tm_nm) >= 2 " +
                "order by dbms_random.value )  " +
                "where rownum < 2 ) ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String groupCode = rs.getValue("grp_cd");
        RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment);
        retrieveTeamsByGroupCode.setgroupcode(groupCode);
        retrieveTeamsByGroupCode.sendRequest();
        TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was 200", retrieveTeamsByGroupCode);
        List<String> teams = retrieveTeamsByGroupCode.getTeamNames();

        // Compare Each Row
        boolean found = false;
        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            for (String name : teams) {
                if (name.equalsIgnoreCase(rs.getValue("grp_tm_nm"))) {
                    found = true;
                    break;
                }
            }
            if (found) {
                TestReporter.softAssertTrue(true, "Team name [ " + rs.getValue("grp_tm_nm") + " ] was found as expected");
            } else {
                TestReporter.softAssertTrue(false, "Team name [ " + rs.getValue("grp_tm_nm") + " ] was not found as expected");
            }
            found = false;
        }

        TestReporter.assertAll();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode" })
    public void testRetrieveTeamsByGroupCodeNoTeamNames() {

        String sql = "select a.grp_cd, b.grp_tm_nm " +
                "from group_mgmt.grp a " +
                "left outer join res_mgmt.grp_tm b on a.grp_cd = b.grp_cd " +
                "where b.grp_tm_nm is null " +
                "and rownum <= 5 " +
                "order by dbms_random.value";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String groupCode = rs.getValue("grp_cd");

        RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment);
        retrieveTeamsByGroupCode.setgroupcode(groupCode);
        retrieveTeamsByGroupCode.sendRequest();
        TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was not 200", retrieveTeamsByGroupCode);

        TestReporter.softAssertNull(retrieveTeamsByGroupCode.getTeamCode(), "Team Names is null as expected ");
        TestReporter.assertAll();
    }
}
