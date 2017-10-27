package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_Cancelled_Positive extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "searchAccommodationsForShare" })
    public void test_SearchAccommodationsForShare_ROSameDay_Cancelled_Positive() {

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "Main");
        search.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        search.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        search.setAccomFacTOResortCode(getResortCode());
        search.setResortCode(getResortCode());
        search.setRoomTypeCode(getRoomTypeCode());
        search.setPostalCode(getHouseHold().primaryGuest().primaryAddress().getZipCode());
        search.setTravelStatus("Cancelled");
        search.sendRequest();

        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred searching accommodation for share", search);

        int count = search.getNumberOfResponseNodesByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return");

        TestReporter.assertTrue(count == 0, "Verify no return nodes are brought back");
    }
}
