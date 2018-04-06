package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.unshareAccommodations;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.UnshareAccommodations;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestUnshareAccommodations_NullRequest extends AccommodationBaseTest {
    private String faultString = "Required parameters are missing";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations", "negative" })
    public void testUnshareAccommodations_NullRequest() {

        UnshareAccommodations unshare = new UnshareAccommodations(environment);
        unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request", BaseSoapCommands.REMOVE_NODE.toString());
        unshare.sendRequest();

        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

}
