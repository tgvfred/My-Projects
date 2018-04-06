package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.removeGroup;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.StageRemoveGroupDataHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RemoveGroup;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class Test_RemoveGroup_RoomOnly extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationsales", "removeGroup" })
    public void test_RemoveGroup_RoomOnly() {
        RemoveGroup removeGroup = new RemoveGroup(environment);
        removeGroup.setRequestNodeValueByXPath("/Envelope/Body/removeGroup/request/externalReference", BaseSoapCommands.REMOVE_NODE.toString());
        removeGroup.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        removeGroup.sendRequest();

        TestReporter.logAPI(!removeGroup.getResponseStatusCode().equals("200"), "There was an error in response: " + removeGroup.getFaultString(), removeGroup);
        StageRemoveGroupDataHelper.validateResMgmtInfo(getBook());
        StageRemoveGroupDataHelper.validateResHistoryInfo(getBook());
        StageRemoveGroupDataHelper.validateTcGuestInfo(getBook());
        StageRemoveGroupDataHelper.validateTPPartyInfo(getBook(), getHouseHold());
        StageRemoveGroupDataHelper.validateRIMInfo(getBook());
        StageRemoveGroupDataHelper.validateFolioRootInfo(getBook());
        StageRemoveGroupDataHelper.validateFolioNodeInfo(getBook());
    }
}