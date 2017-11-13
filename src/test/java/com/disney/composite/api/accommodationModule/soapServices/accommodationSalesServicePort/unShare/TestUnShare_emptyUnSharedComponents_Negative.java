package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestUnShare_emptyUnSharedComponents_Negative extends AccommodationBaseTest {

    private UnShare unshare;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_emptyUnSharedComponents_Negative() {
        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }
        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        unshare.sendRequest();

        String faultString = "Accommodations not found : No TravelComponentGrouping found";

        validateApplicationError(unshare, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);

        TestReporter.assertEquals(unshare.getFaultString(), faultString, "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }
}
