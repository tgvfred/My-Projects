package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_checkingInFirstRes extends AccommodationBaseTest {
    private Share share;
    private ReplaceAllForTravelPlanSegment book;
    private String firstTCG;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());
        setValues();
        bookReservation();
        book = getBook();
        firstTCG = getBook().getTravelComponentGroupingId();
        setDaysOut(1);
        setArrivalDate(getDaysOut());
        setNights(2);
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void testShare_twoTcg_checkingInFirstRes() {
        String faultString = "Accommodation should be in Booked status to be Shared : Accommodation not in Booked Status";
        // check in the first res
        CheckInHelper checkingIn = new CheckInHelper(environment, book);
        checkingIn.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.assertTrue(share.getFaultString().contains(faultString), "Verify that the fault string [" + share.getFaultString() + "] contains that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED);
    }
}