package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestReinstate_nullRequest_Negative extends AccommodationBaseTest {

    Reinstate reinstate;

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void Test_Reinstate_nullRequest_Negative() {

        reinstate = new Reinstate(environment, "Main_2");
        reinstate.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.sendRequest();

        String faultstring = "Required parameters are missing : null";

        validateApplicationError(reinstate, LiloResmErrorCode.REQUIRED_PARAMETERS_MISSING);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
