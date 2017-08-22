package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationPolicy;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.diningBatchModule.diningBatchService.operations.AutoCancel;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy_autoCancelled extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_autoCancelled() {

        String faultString = "Cannot calculate Cancel fee";

        AutoCancel autoCancel = new AutoCancel(getEnvironment(), "Main");
        autoCancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        autoCancel.sendRequest();

        TestReporter.logAPI(!autoCancel.getResponseStatusCode().equals("200"), "An error occurred when sending request", autoCancel);

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALC_CANCEL_FEE);

    }
}
