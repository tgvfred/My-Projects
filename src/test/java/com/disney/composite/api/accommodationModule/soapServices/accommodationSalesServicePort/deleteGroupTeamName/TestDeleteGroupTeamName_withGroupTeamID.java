package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.deleteGroupTeamName;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestDeleteGroupTeamName_withGroupTeamID extends AccommodationBaseTest {

    DeleteGroupTeamName delete;
    String name = Randomness.randomString(6);

    @Test(groups = { "api", "regression", "deleteGroupTeamName", "accommodation", "accommodatoinsales" })
    public void Test_DeleteGroupTeamName_withGroupTeamID() {

        delete = new DeleteGroupTeamName(environment, "_Main");
        delete.setGroupName(name);
        delete.setgroupTeamName(name);
        delete.setSelected("true");
        delete.sendRequest();
        TestReporter.logAPI(!delete.getResponseStatusCode().equals("200"), "An error occurred while creating a group team name: " + delete.getFaultString(), delete);

    }
}
