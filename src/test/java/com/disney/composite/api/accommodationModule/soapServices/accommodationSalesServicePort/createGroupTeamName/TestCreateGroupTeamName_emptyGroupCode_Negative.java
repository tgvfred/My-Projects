package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateGroupTeamName_emptyGroupCode_Negative extends AccommodationBaseTest {

    CreateGroupTeamName create;

    @Test(groups = { "api", "regression", "createGroupTeamName", "accommodation", "accommodationsales", "negative" })
    public void Test_CreateGroupTeamName_emptyGroupCode_Negative() {

        create = new CreateGroupTeamName(environment, "Main");
        create.setGroupCode(" ");
        create.setGroupName(Randomness.randomString(6));
        create.setGroupTeamName(Randomness.randomString(6));
        create.setSelected("true");
        create.sendRequest();

        String faultstring = "Error while trying create GroupTeam : Invalid request. Group Code is null !";

        validateApplicationError(create, AccommodationErrorCode.CREATE_GROUP_TEAM);

        TestReporter.assertEquals(faultstring, create.getFaultString(), "Verify that the fault string [" + create.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
