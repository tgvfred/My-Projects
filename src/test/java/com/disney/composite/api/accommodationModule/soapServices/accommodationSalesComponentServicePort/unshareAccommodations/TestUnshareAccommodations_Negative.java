package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.unshareAccommodations;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.UnshareAccommodations;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestUnshareAccommodations_Negative extends BaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations", "negative" })
    public void testUnshareAccommodations_NullRequest() {

        String faultString = "Required Parameters are missing";

        UnshareAccommodations unshare = new UnshareAccommodations(environment);
        unshare.setRequestNodeValueByXPath("/Envelope/Body/shareAccommodations/request", BaseSoapCommands.REMOVE_ATTRIBUTE.toString());
        unshare.sendRequest();

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations", "negative" })
    public void testUnshareAccommodations_NoAccommFound() {

        String faultString = "Accommodations not found";

        UnshareAccommodations unshare = new UnshareAccommodations(environment);
        unshare.setRequestNodeValueByXPath("/Envelope/Body/shareAccommodations/request", BaseSoapCommands.REMOVE_ATTRIBUTE.toString());
        unshare.sendRequest();

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

}
