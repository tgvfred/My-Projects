package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.deleteGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestDeleteGroupTeamName_withoutGroupTeamID extends AccommodationBaseTest {
    CreateGroupTeamName create;
    DeleteGroupTeamName delete;
    String name = Randomness.randomString(6);

    @Test(groups = { "api", "regression", "deleteGroupTeamName", "accommodation", "accommodatoinsales" })
    public void Test_DeleteGroupTeamName_withoutGroupTeamID() {
        create = new CreateGroupTeamName(environment, "Main");
        create.setGroupCode("01825");
        create.setGroupName(name);
        create.setGroupTeamName(name);
        create.setSelected("false");
        create.sendRequest();
        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred while creating a group team name: " + create.getFaultString(), create);

        delete = new DeleteGroupTeamName(environment, "_Main");
        delete.setgroupCode("01825");
        delete.setGroupTeamId(BaseSoapCommands.REMOVE_NODE.toString());
        delete.setGroupName(name);
        delete.setgroupTeamName(name);
        delete.setSelected("false");
        delete.sendRequest();
        TestReporter.logAPI(!delete.getResponseStatusCode().equals("200"), "An error occurred while creating a group team name: " + delete.getFaultString(), delete);

        // validate that the teamNameDeleted field is "true".
        TestReporter.assertEquals(delete.getTeamNameDeleted(), "true", "Validate that the team name deleted field returned in the response [" + delete.getTeamNameDeleted() + "] is that which is expected [true].");
        validateResponse();
    }

    public void validateResponse() {
        TestReporter.logStep("Validate the team name was successfully deleted in the database.");

        String sql = "select * "
                + "from res_mgmt.grp_tm a "
                + "where a.GRP_TM_NM = '" + name + "'";

        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        boolean deleteFlag = false;

        if (rs.getRowCount() == 0) {
            TestReporter.assertTrue(rs.getRowCount() == 0, "Validate that the group team name was deleted out of the DB.");
            deleteFlag = true;
        } else {
            TestReporter.assertFalse(deleteFlag, "Error: the group team name was not deleted out of the DB.");
        }

    }

}
