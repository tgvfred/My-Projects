package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.deleteGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestDeleteGroupTeamName_nullGroupCode_Negative extends AccommodationBaseTest {

    DeleteGroupTeamName delete;

    @Test(groups = { "api", "regression", "deleteGroupTeamName", "accommodation", "accommodatoinsales", "negative" })
    public void Test_DeleteGroupTeamName_nullGroupCode_Negative() {

        delete = new DeleteGroupTeamName(environment, "_Main");
        delete.setgroupCode(BaseSoapCommands.REMOVE_NODE.toString());
        delete.setGroupName("Marbles3");
        // delete.setGroupTeamId(BaseSoapCommands.REMOVE_NODE.toString());
        delete.setgroupTeamName("Marbles3");
        delete.setSelected("true");
        delete.sendRequest();

        String faultstring = "Invalid Delete Team Name Request : Invalid request. Mandatory fields not keyed in!";

        validateApplicationError(delete, AccommodationErrorCode.DELETE_TEAM_NAME);

        TestReporter.assertEquals(faultstring, delete.getFaultString(), "Verify that the fault string [" + delete.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}