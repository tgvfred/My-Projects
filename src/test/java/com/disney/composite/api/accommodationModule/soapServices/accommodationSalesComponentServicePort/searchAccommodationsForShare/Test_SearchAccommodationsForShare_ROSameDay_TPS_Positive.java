package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_TPS_Positive extends AccommodationBaseTest {

    @Test
    public void test_SearchAccommodationsForShare_ROSameDay_TPS_Positive() {

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "Main");
        search.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        search.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        search.setTravelPlanId(getBook().getTravelPlanId());
        search.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        search.setAccomFacTOResortCode(getResortCode());
        search.setResortCode(getResortCode());
        search.setRoomTypeCode(getRoomTypeCode());
        search.sendRequest();

        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred searching accommodation for share", search);

    }
}
