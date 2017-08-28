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

public class TestUnShare_twoTcg_differentResorts_Negative extends AccommodationBaseTest {

    private UnShare unshare;
    private Share share;
    String tpId;
    String tcgId;
    String firstTcg;

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
        firstTcg = getBook().getTravelComponentGroupingId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_twoTcg_differentResorts_Negative() {

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTcg);
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTcg);
        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);

        // book second reservation.
        setDaysOut(1);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");

        String previousResort = getResortCode();
        String previousRoomTypeCode = getRoomTypeCode();
        // Loop until
        // 1. The resort is the same
        // 2. The room type has changed for the original resort
        do {
            setValues();
        } while (!getResortCode().equals(previousResort) && getRoomTypeCode().equals(previousRoomTypeCode));
        setSendRequest(false);
        bookReservation();
        getBook().setEnvironment(Environment.getBaseEnvironmentName(environment));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        unshare.sendRequest();
        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + unshare.getFaultString(), unshare);

        String faultString = "Travel Component Id is required : InventoryService::getShareChain:No AccommodationComponent object found for TravelComponentGrouping ID: " + tcgId + "";

        validateApplicationError(unshare, AccommodationErrorCode.TRVL_PLAN_COMPONENT_ID_REQ);

        TestReporter.assertEquals(unshare.getFaultString(), faultString, "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}
