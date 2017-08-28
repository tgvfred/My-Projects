package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_TcgLessThanZero extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_TcgLessThanZero() {
        TestReporter.logScenario("Test Cancel TCG less than zero");

        String faultString = "Required parameters are missing : TravelComponentGrouping Id or ExternalReferenceDetail is required";

        Cancel cancel = new Cancel(environment, "MainCancel");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId("-12345");
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, AccommodationErrorCode.REQUIRED_PARAM_MISSING);
    }
}