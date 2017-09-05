package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_InvalidTcg extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_InvalidTcg() {
        TestReporter.logScenario("Test Cancel Invalid Tcg");

        String faultString = "Accommodations not found :";

        Cancel cancel = new Cancel(environment, "MainCancel");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId("123654789987456321");
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().contains(faultString), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);
    }
}
