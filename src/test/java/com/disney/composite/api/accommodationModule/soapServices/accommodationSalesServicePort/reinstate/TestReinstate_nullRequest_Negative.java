package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestReinstate_nullRequest_Negative extends AccommodationBaseTest {
    private Reinstate reinstate;
    private String locEnv;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        locEnv = environment;
    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void Test_Reinstate_nullRequest_Negative() {

        reinstate = new Reinstate(locEnv, "Main_2");
        reinstate.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.sendRequest();

        String faultstring = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(reinstate, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
