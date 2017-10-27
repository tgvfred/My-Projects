package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.SearchAccommodationsForShareHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_ExtRef_Positive extends AccommodationBaseTest {

    private ReplaceAllForTravelPlanSegment book;

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
        // setSkipExternalRef(false);
        bookReservation();

        book = getBook();
    }

    @Test
    public void test_SearchAccommodationsForShare_ROSameDay_ExtRef_Positive() {

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "MainWithExtRef");
        search.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        search.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        search.setAccomFacTOResortCode(getResortCode());
        search.setResortCode(getResortCode());
        search.setRoomTypeCode(getRoomTypeCode());
        search.setExternalReferenceNumber(getExternalRefNumber());
        search.setExternalReferenceSource(getExternalRefSource());
        search.setPostalCode(getHouseHold().primaryGuest().primaryAddress().getZipCode());
        search.sendRequest();

        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred searching accommodation for share", search);

        SearchAccommodationsForShareHelper helper = new SearchAccommodationsForShareHelper(environment);
        helper.matchReservationInfoWithResponseInfo(search, book, 1);
        helper.validateReturnNodeCount(search, 1);

        if (Environment.isSpecialEnvironment(environment)) {
            SearchAccommodationsForShare clone = (SearchAccommodationsForShare) search.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));

            int tries = 0;
            int maxTries = 10;
            boolean success = false;
            do {
                Sleeper.sleep(1000);
                try {
                    clone.sendRequest();
                    success = true;
                } catch (Exception e) {

                }
                tries++;
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(search, true), "Validating Response Comparison");
        }
    }
}
