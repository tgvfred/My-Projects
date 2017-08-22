package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestShare_oneTcg_invalidTcg_Negative extends AccommodationBaseTest {

    private Share share;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_oneTcg_invalidTcg_Negative() {

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId("1234");
        share.sendRequest();

        String faultString = " No Accommodation Component found. : null";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.NO_ACCOMMODATION_COMPONENT_EXCEPTION);
    }

}
