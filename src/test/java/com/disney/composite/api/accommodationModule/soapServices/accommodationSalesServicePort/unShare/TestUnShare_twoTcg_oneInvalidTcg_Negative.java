package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestUnShare_twoTcg_oneInvalidTcg_Negative extends AccommodationBaseTest {

    private UnShare unshare;
    private Share share;
    private String firstTCG;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        getBook().setEnvironment(Environment.getBaseEnvironmentName(environment));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

        firstTCG = getBook().getTravelComponentGroupingId();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_twoTcg_oneInvalidTcg_Negative() {

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTCG);
        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId("1234");
        unshare.sendRequest();

        String faultString = "Travel Component Id is required : InventoryService::getShareChain:No AccommodationComponent object found for TravelComponentGrouping ID: 1234";

        validateApplicationError(unshare, AccommodationErrorCode.TRVL_PLAN_COMPONENT_ID_REQ);

        TestReporter.assertEquals(unshare.getFaultString(), faultString, "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}
