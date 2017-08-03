package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCPointsHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.travelPlanSegment.SplitToTravelPlanHelper;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_differentResorts_Negative extends BookDVCPointsHelper {

    private Share share;
    private SplitToTravelPlanHelper split;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        setUseDvcResort(true);
        setUseNonZeroPoints(true);
        bookDvcReservation("testSplitToTravelPlan_CheckedIn_DifferentResorts", 1);

        setUseExistingValues(false);
        bookDvcReservation("testSplitToTravelPlan_CheckedIn_DifferentResorts", 2);
        Sleeper.sleep(10000);
        split = new SplitToTravelPlanHelper(getFirstBooking(), getSecondBooking(), environment);
        split.merge();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_twoTcg_differentResorts_Negative() {

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        share.setSecondTravelComponentGroupingId(getSecondBooking().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE , PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.CANNOT_CHANGE_RESORT);
    }

}
