package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee_nullIdentityDetails extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_nullIdentityDetails() {

        String faultString = "Travel Plan Segment Not Found : Travel Plan Segment Should not be NULL";

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "Main");
        retrieve.setIdentityDetails(BaseSoapCommands.REMOVE_ATTRIBUTE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().contains(faultString), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, LiloResmErrorCode.TRVL_PLAN_SEG_NOT_FOUND);
    }
}
