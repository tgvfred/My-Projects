package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.utils.TestReporter;

public class SearchAccommodationsForShareHelper {

    private String environment;

    public SearchAccommodationsForShareHelper(String environment) {
        this.environment = environment;
    }

    public void matchReservationInfoWithResponseInfo(SearchAccommodationsForShare search, ReplaceAllForTravelPlanSegment book, int count) {

        TestReporter.softAssertEquals(search.getResortCode(), book.getResortCode(), "Validate resort code from the reservartion: [" + book.getResortCode() + "] matches the "
                + "resort code from the SearchAccommodationsForShare response: [" + search.getResortCode() + "]");
        TestReporter.softAssertEquals(search.getRoomTypeCode(), book.getRoomTypeCode(), "Validate room type code from the reservartion: [" + book.getRoomTypeCode() + "] matches the "
                + "room type code from the SearchAccommodationsForShare response: [" + search.getRoomTypeCode() + "]");
        TestReporter.softAssertEquals(search.getTravelComponentGroupingId(), book.getTravelComponentGroupingId(), "Validate TcgID from the reservartion: [" + book.getTravelComponentGroupingId() + "] matches the "
                + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId() + "]");
        TestReporter.softAssertEquals(search.getTravelComponentId(), book.getTravelComponentId(), "Validate TcID from the reservartion: [" + book.getTravelComponentId() + "] matches the "
                + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId() + "]");
        TestReporter.softAssertEquals(search.getTravelPlanSegmentId(), book.getTravelPlanSegmentId(), "Validate TpsID from the reservartion: [" + book.getTravelPlanSegmentId() + "] matches the "
                + "TpsID from the SearchAccommodationsForShare response: [" + search.getTravelPlanSegmentId() + "]");
        TestReporter.softAssertEquals(search.getFirstName(), book.getFirstName(), "Validate guest first name from the reservartion: [" + book.getFirstName() + "] matches the "
                + "guest first name from the SearchAccommodationsForShare response: [" + search.getFirstName() + "]");
        TestReporter.softAssertEquals(search.getLastName(), book.getLastName(), "Validate guest last name from the reservartion: [" + book.getLastName() + "] matches the "
                + "guest last name from the SearchAccommodationsForShare response: [" + search.getLastName() + "]");
        TestReporter.softAssertEquals(search.getTravelStatus(), book.getTravelStatus(), "Validate travel status from the reservartion: [" + book.getTravelStatus() + "] matches the "
                + "travel status from the SearchAccommodationsForShare response: [" + search.getTravelStatus() + "]");
        TestReporter.softAssertEquals(search.getEndDate(), book.getEndDate(), "Validate end date from the reservartion: [" + book.getEndDate() + "] matches the "
                + "end date from the SearchAccommodationsForShare response: [" + search.getEndDate() + "]");
        TestReporter.softAssertEquals(search.getStartDate(), book.getStartDate(), "Validate start date from the reservartion: [" + book.getStartDate() + "] matches the "
                + "start date from the SearchAccommodationsForShare response: [" + search.getStartDate() + "]");

    }

    public void validateReturnNodeCount(SearchAccommodationsForShare search, int expectedCount) {

        int count = search.getNumberOfResponseNodesByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return");

        if (count == expectedCount) {
            TestReporter.assertEquals(count, expectedCount, "Verify return node count is as expected.  Count: [" + count + "]");
        } else {
            TestReporter.assertTrue(count == expectedCount, "Return node count: [" + count + "] differs from expected count: [" + expectedCount + "]");
        }
    }

}
