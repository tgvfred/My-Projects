package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.deleteGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestDeleteGroupTeamName_emptyGroupTeamName_Negative extends AccommodationBaseTest {

    DeleteGroupTeamName delete;

    @Test(groups = { "api", "regression", "deleteGroupTeamName", "accommodation", "accommodatoinsales", "negative" })
    public void Test_DeleteGroupTeamName_emptyGroupTeamName_Negative() {

        delete = new DeleteGroupTeamName(Environment.getBaseEnvironmentName(environment), "_Main");
        delete.setGroupName("Marbles2");
        delete.setgroupTeamName(" ");
        delete.setSelected("true");
        delete.sendRequest();

        String faultstring = "Invalid Delete Team Name Request : Invalid request. Mandatory fields not keyed in!";

        validateApplicationError(delete, LiloResmErrorCode.INVALID_DELETE_TEAM_REQUEST);

        TestReporter.assertEquals(faultstring, delete.getFaultString(), "Verify that the fault string [" + delete.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }
}
