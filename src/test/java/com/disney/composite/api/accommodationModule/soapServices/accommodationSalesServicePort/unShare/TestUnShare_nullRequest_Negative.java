package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestUnShare_nullRequest_Negative extends AccommodationBaseTest {

    private UnShare unshare;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_nullRequest_Negative() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }
        unshare = new UnShare(environment, "Main");
        unshare.setRequestNodeValueByXPath("/Envelope/Body/unShare/request/unSharedComponents", BaseSoapCommands.REMOVE_NODE.toString());
        unshare.sendRequest();

        String faultString = "Atleast one TravelComponentGroupingId Should be Provided for unshare";

        validateApplicationError(unshare, AccommodationErrorCode.INVALID_REQUEST);

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}