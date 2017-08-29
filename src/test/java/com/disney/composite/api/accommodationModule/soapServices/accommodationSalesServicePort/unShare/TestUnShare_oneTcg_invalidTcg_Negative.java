package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestUnShare_oneTcg_invalidTcg_Negative extends AccommodationBaseTest {

    private UnShare unshare;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void testUnShare_oneTcg_invalidTcg_Negative() {

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId("1234");
        unshare.sendRequest();

        String faultString = "Travel Component Id is required : InventoryService::getShareChain:No AccommodationComponent object found for TravelComponentGrouping ID: 1234";

        validateApplicationError(unshare, AccommodationErrorCode.TRVL_PLAN_COMPONENT_ID_REQ);

        TestReporter.assertEquals(unshare.getFaultString(), faultString, "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }
}
