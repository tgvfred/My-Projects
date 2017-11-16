package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.SearchAccommodationsForShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_AlreadyShared extends AccommodationBaseTest {

    private Share share;

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
    public void test_SearchAccommodationsForShare_ROSameDay_AlreadyShared() {
        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

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
