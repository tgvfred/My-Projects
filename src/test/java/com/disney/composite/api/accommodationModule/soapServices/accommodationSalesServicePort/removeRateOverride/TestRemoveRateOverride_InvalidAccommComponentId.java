package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.removeRateOverride;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RemoveRateOverride;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestRemoveRateOverride_InvalidAccommComponentId extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void testRemoveRateOverride_InvalidAccommComponentId() {
        String faultString = "Required parameters are missing";

        RemoveRateOverride remove = new RemoveRateOverride(environment, "removeRateOverride");
        remove.settravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        remove.setaccomComponentId("-1");
        remove.setlocationId("0");
        remove.sendRequest();

        TestReporter.logAPI(!remove.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + remove.getFaultString() + " ]", remove);
        validateApplicationError(remove, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }
}