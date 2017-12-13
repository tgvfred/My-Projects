package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee_nullIdentityLevel extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_nullIdentityLevel() {

        String faultString = "Travel Plan Segment Not Found : Travel Plan Segment Should not be NULL";

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "Main");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setIdentityLevel(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().contains(faultString), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.TRAVEL_PLAN_SEGMENT_NOT_FOUND);
    }
}
