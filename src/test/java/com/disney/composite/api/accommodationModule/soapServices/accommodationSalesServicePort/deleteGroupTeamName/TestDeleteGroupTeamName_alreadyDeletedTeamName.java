package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.deleteGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestDeleteGroupTeamName_alreadyDeletedTeamName extends AccommodationBaseTest {

    CreateGroupTeamName create;
    DeleteGroupTeamName delete;
    String name = "Purple3";

    @Test(groups = { "api", "regression", "deleteGroupTeamName", "accommodation", "accommodatoinsales" })
    public void Test_DeleteGroupTeamName_alreadyDeletedTeamName() {

        delete = new DeleteGroupTeamName(environment, "_Main");
        delete.setGroupName(name);
        delete.setgroupTeamName(name);
        delete.setSelected("false");
        delete.sendRequest();
        TestReporter.logAPI(!delete.getResponseStatusCode().equals("200"), "An error occurred while creating a group team name: " + delete.getFaultString(), delete);

        // validate that the teamNameDeleted field is "true".
        TestReporter.assertEquals(delete.getTeamNameDeleted(), "false", "Validate that the team name deleted field returned in the response [" + delete.getTeamNameDeleted() + "] is that which is expected [false].");
        validateResponse();
    }

    public void validateResponse() {
        TestReporter.logStep("Validate the team name was successfully deleted in the database.");

        String sql = "select * "
                + "from res_mgmt.grp_tm a "
                + "where a.GRP_TM_NM = '" + name + "'";

        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertTrue(rs.getRowCount() == 0, "Validate that the group team name was already deleted out of the DB.");

    }

}
