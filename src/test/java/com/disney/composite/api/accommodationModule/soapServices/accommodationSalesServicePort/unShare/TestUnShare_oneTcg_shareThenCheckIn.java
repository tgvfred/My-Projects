package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestUnShare_oneTcg_shareThenCheckIn extends AccommodationBaseTest {
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
    public void Test_unShare_oneTcg_shareThenCheckIn() {
        String faultString = " Accommodation should be in Booked status to be UnShared : Accommodation not in Booked Status";
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        CheckIn checkIn = new CheckIn(environment, "Main");
        checkIn.setTravelComponentGroupingId(firstTCG);
        checkIn.setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/checkInGuestDetails/guestId", BaseSoapCommands.REMOVE_NODE.toString());
        checkIn.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in a reservation " + share.getFaultString(), share);

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTCG);
        unshare.setLocationId(getLocationId());
        unshare.sendRequest();
        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] contains that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.ACCOMM_NOT_BOOKED_STATUS_UNSHARED);
    }
}