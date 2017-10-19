package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestCreateGroupTeamName_existingGroupCodeandName_Negative extends AccommodationBaseTest {

    CreateGroupTeamName create;

    @Test(groups = { "api", "regression", "createGroupTeamName", "accommodation", "accommodationsales", "negative" })
    public void Test_CreateGroupTeamName_existingGroupCodeandName_Negative() {

        create = new CreateGroupTeamName(environment, "Main");
        create.setGroupCode("01825");
        create.setGroupName("Marbles2");
        create.setGroupTeamName("Marbles2");
        create.setGroupTeamId("12756700");
        create.setSelected("true");
        create.sendRequest();

        String faultstring = "Error while trying create GroupTeam : Invalid request. Group Code is null !";

        validateApplicationError(create, AccommodationErrorCode.CREATE_GROUP_TEAM);

        TestReporter.assertEquals(faultstring, create.getFaultString(), "Verify that the fault string [" + create.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
