package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_checkingInBothRes extends AccommodationBaseTest {
    private Share share;
    private ReplaceAllForTravelPlanSegment book, book1;
    private String firstOwnerId;
    private String secondOwnerId;
    private String firstTCG;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();
        book = getBook();
        firstTCG = getBook().getTravelComponentGroupingId();

        bookReservation();
        book1 = getBook();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_twoTcg_checkingInBothRes() {
        String faultString = "Accommodation should be in Booked status to be Shared : Accommodation not in Booked Status";

        CheckInHelper checkingIn = new CheckInHelper(environment, book);
        checkingIn.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        checkingIn = new CheckInHelper(environment, book1);
        checkingIn.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        // verify that the owner id's for the first and second tcg do not match.
        TestReporter.softAssertTrue(firstOwnerId != secondOwnerId, "Verify the assignment owner Ids for each TCG [" + firstOwnerId + "] do not match [" + secondOwnerId + "].");

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.assertTrue(share.getFaultString().contains(faultString), "Verify that the fault string [" + share.getFaultString() + "] contains that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.ACCOMM_NOT_BOOKED_STATUS);
    }
}