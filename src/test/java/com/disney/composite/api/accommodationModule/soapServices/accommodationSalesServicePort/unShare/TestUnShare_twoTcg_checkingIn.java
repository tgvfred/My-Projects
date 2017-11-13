package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;

public class TestUnShare_twoTcg_checkingIn extends AccommodationBaseTest {
    private UnShare unshare;
    private Share share;
    private String firstTCG;
    private ReplaceAllForTravelPlanSegment book;
    private ReplaceAllForTravelPlanSegment book2;

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
        book = getBook();

        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        firstTCG = getBook().getTravelComponentGroupingId();

        // book second reservation.
        setDaysOut(0);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        isComo.set("true");
        setSendRequest(false);
        bookReservation();
        book2 = getBook();

        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_twoTcgs_checkingIn() {
        String faultString = " Accommodation should be in Booked status to be UnShared : Accommodation not in Booked Status";
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        CheckInHelper checkingIn = new CheckInHelper(environment, book);
        checkingIn.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        checkingIn = new CheckInHelper(environment, book2);
        checkingIn.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(firstTCG);
        unshare.setLocationId(getLocationId());
        unshare.sendRequest();
        TestReporter.assertTrue(unshare.getFaultString().contains(faultString), "Verify that the fault string [" + unshare.getFaultString() + "] contains that which is expected [" + faultString + "].");
        validateApplicationError(unshare, AccommodationErrorCode.ACCOMM_NOT_BOOKED_STATUS_UNSHARED);
    }
}