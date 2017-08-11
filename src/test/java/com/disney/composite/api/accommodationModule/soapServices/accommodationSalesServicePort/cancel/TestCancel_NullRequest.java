package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_NullRequest extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_NullRequest() {
        TestReporter.logScenario("Test Cancel Null Request");

        String faultString = "Required parameters are missing : null";

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setRequest(BaseSoapCommands.REMOVE_NODE.toString());
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, LiloResmErrorCode.REQ_PARAM_MISSING);
    }
}
