package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.SearchAccommodationsForShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_CheckedIn extends AccommodationBaseTest {

    private int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        int tries = 0;
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();
        checkingIn(Environment.getBaseEnvironmentName(getEnvironment()));

        CheckIn checkIn = new CheckIn(Environment.getBaseEnvironmentName(getEnvironment()), "UI_Booking");
        checkIn.setGuestId(getBook().getGuestId());
        checkIn.setLocationId(getLocationId());
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkIn.sendRequest();
        if (checkIn.getFaultString().contains("Row was updated or deleted by another transaction")) {
            maxTries = 5;
            tries = 0;
            do {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
                checkIn.sendRequest();
                tries++;
            } while (checkIn.getFaultString().contains("Row was updated or deleted by another transaction") && tries < maxTries);
        }
        TestReporter.assertTrue(checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getBook().getTravelPlanId() + "]: " + checkIn.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "searchAccommodationsForShare" })
    public void test_SearchAccommodationsForShare_ROSameDay_CheckedIn() {

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "Main");
        search.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        search.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        search.setAccomFacTOResortCode(getResortCode());
        search.setResortCode(getResortCode());
        search.setRoomTypeCode(getRoomTypeCode());
        search.setPostalCode(getHouseHold().primaryGuest().primaryAddress().getZipCode());
        search.sendRequest();

        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred searching accommodation for share", search);

        int count = search.getNumberOfResponseNodesByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return");
        SearchAccommodationsForShareHelper helper = new SearchAccommodationsForShareHelper(environment);
        helper.validateReturnNodeCountOneOrGreater(search, getBook());
        helper.matchReservationInfoWithResponseInfo(search, getBook(), count);

        if (Environment.isSpecialEnvironment(getEnvironment())) {
            SearchAccommodationsForShare clone = search;
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
            clone.sendRequest();
            TestReporter.assertTrue(clone.getResponseStatusCode().equals("200"), "Verify that no issue occurred cloning: " + clone.getFaultString());
            clone.validateResponseNodeQuantity(search, true);
        }
    }
}
