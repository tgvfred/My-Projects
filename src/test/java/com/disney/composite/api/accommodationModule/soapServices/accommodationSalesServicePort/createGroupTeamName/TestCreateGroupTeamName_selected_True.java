package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCreateGroupTeamName_selected_True extends AccommodationBaseTest {

    CreateGroupTeamName create;
    String name = Randomness.randomString(6);

    @Test(groups = { "api", "regression", "createGroupTeamName", "accommodation", "accommodationsales" })
    public void Test_CreateGroupTeamName_selected_True() {

        create = new CreateGroupTeamName(environment, "Main");
        create.setGroupName(name);
        create.setGroupTeamName(name);
        create.setSelected("true");
        create.sendRequest();
        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred while creating a group team name: " + create.getFaultString(), create);

        // validate that the selected field is "true".
        TestReporter.assertEquals(create.getSelected(), "true", "Validate that the selected field returned in the response [" + create.getSelected() + "] is that which is expected [true].");
        validateResponse();

    }

    public void validateResponse() {
        TestReporter.logStep("Validate the response and database values.");

        String sql = "select a.GRP_TM_ID, a.GRP_CD, a.GRP_TM_NM "
                + "from res_mgmt.grp_tm a "
                + "where a.GRP_TM_NM = '" + name + "'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertEquals(rs.getValue("GRP_TM_NM"), create.getGroupTeamName(), "Validate that the group team name returned in the response [" + create.getGroupTeamName() + "] is that which is expected [" + rs.getValue("GRP_TM_NM") + "].");
        TestReporter.assertEquals(rs.getValue("GRP_CD"), create.getGroupCode(), "Validate that the group code returned in the response [" + create.getGroupCode() + "] is that which is expected [" + rs.getValue("GRP_CD") + "].");

    }

}
