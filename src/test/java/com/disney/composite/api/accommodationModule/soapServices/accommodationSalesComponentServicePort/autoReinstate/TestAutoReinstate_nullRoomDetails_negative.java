package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_nullRoomDetails_negative extends AccommodationBaseTest {

    AutoReinstate auto;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate", "negative" })
    public void Test_AutoReinstate_nullRoomDetails_negative() {

        auto = new AutoReinstate(environment, "Main");
        auto.setFreezeId(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setInventoryOverrideReason(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setInventoryOverrideContactName(BaseSoapCommands.REMOVE_NODE.toString());
        auto.sendRequest();

        String faultString = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(auto, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
        TestReporter.assertEquals(faultString, auto.getFaultString(), "Verify that the fault string [" + auto.getFaultString() + "] is that which is expected.[" + faultString + "]");
    }

}
