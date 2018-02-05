package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_checkInThenShare_Negative extends AccommodationBaseTest {

    private Share share;
    private ReplaceAllForTravelPlanSegment book, book1;
    String firstOwnerId;
    String secondOwnerId;
    String firstTCG;
    String ownerIdOne;
    String ownerIdTwo;
    String guestId;
    String guestId2;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting

        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();
        ReplaceAllForTravelPlanSegment book = getBook();
        firstTCG = getBook().getTravelComponentGroupingId();
        guestId = getBook().getGuestId();

        bookReservation();
        ReplaceAllForTravelPlanSegment book1 = getBook();
        guestId2 = getBook().getGuestId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share" })
    public void Test_Share_twoTcg_checkingInThenShare_Negative() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }
        // check in the first res
        CheckIn checkIn = new CheckIn(environment, "Main");
        checkIn.setTravelComponentGroupingId(firstTCG);
        checkIn.setGuestId(guestId);

        // Add a wait to avoid async issues
        Sleeper.sleep(10000);

        checkIn.sendRequest();
        TestReporter.logAPI(!checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in a reservation " + checkIn.getFaultString(), checkIn);

        Sleeper.sleep(10000);
        // check in the second res
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkIn.setGuestId(guestId2);
        checkIn.sendRequest();
        TestReporter.logAPI(!checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in the second reservation " + checkIn.getFaultString(), checkIn);

        Sleeper.sleep(10000);
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = " Accommodation should be in Booked status to be Shared : Accommodation not in Booked Status";

        validateApplicationError(share, AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED);
        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(firstTCG);
            cancel(getBook().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
    }
}
