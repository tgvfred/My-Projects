package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.utils.TestReporter;

public class SearchAccommodationsForShareHelper {

    private String environment;
    private int flag;

    public SearchAccommodationsForShareHelper(String environment) {
        this.environment = environment;
    }

    public void matchReservationInfoWithResponseInfo(SearchAccommodationsForShare search, ReplaceAllForTravelPlanSegment book, int count, String tcgId, String tcId) {

        for (int i = 1; i <= count; i++) {
            TestReporter.softAssertEquals(search.getResortCode(String.valueOf(i)), book.getResortCode(), "Validate resort code from the reservartion: [" + book.getResortCode() + "] matches the "
                    + "resort code from the SearchAccommodationsForShare response: [" + search.getResortCode(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getRoomTypeCode(String.valueOf(i)), book.getRoomTypeCode(), "Validate room type code from the reservartion: [" + book.getRoomTypeCode() + "] matches the "
                    + "room type code from the SearchAccommodationsForShare response: [" + search.getRoomTypeCode(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelPlanSegmentId(String.valueOf(i)), book.getTravelPlanSegmentId(), "Validate TpsID from the reservartion: [" + book.getTravelPlanSegmentId() + "] matches the "
                    + "TpsID from the SearchAccommodationsForShare response: [" + search.getTravelPlanSegmentId(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getFirstName(String.valueOf(i)), book.getFirstName(), "Validate guest first name from the reservartion: [" + book.getFirstName() + "] matches the "
                    + "guest first name from the SearchAccommodationsForShare response: [" + search.getFirstName(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getLastName(String.valueOf(i)), book.getLastName(), "Validate guest last name from the reservartion: [" + book.getLastName() + "] matches the "
                    + "guest last name from the SearchAccommodationsForShare response: [" + search.getLastName(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelStatus(String.valueOf(i)), book.getTravelStatus(), "Validate travel status from the reservartion: [" + book.getTravelStatus() + "] matches the "
                    + "travel status from the SearchAccommodationsForShare response: [" + search.getTravelStatus(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getEndDate(String.valueOf(i)), book.getEndDate(), "Validate end date from the reservartion: [" + book.getEndDate() + "] matches the "
                    + "end date from the SearchAccommodationsForShare response: [" + search.getEndDate(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getStartDate(String.valueOf(i)), book.getStartDate(), "Validate start date from the reservartion: [" + book.getStartDate() + "] matches the "
                    + "start date from the SearchAccommodationsForShare response: [" + search.getStartDate(String.valueOf(i)) + "]");
            TestReporter.assertAll();
        }

        if (search.getTravelComponentGroupingId(String.valueOf(2)).equals(book.getTravelComponentGroupingId())) {
            TestReporter.softAssertEquals(search.getTravelComponentGroupingId(String.valueOf(2)), book.getTravelComponentGroupingId(), "Validate TcgID from the reservartion: [" + book.getTravelComponentGroupingId() + "] matches the "
                    + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId(String.valueOf(2)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentId(String.valueOf(2)), book.getTravelComponentId(), "Validate TcID from the reservartion: [" + book.getTravelComponentId() + "] matches the "
                    + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId(String.valueOf(2)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentGroupingId(String.valueOf(1)), tcgId, "Validate TcgID from the reservartion: [" + tcgId + "] matches the "
                    + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId(String.valueOf(1)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentId(String.valueOf(1)), tcId, "Validate TcID from the reservartion: [" + tcId + "] matches the "
                    + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId(String.valueOf(1)) + "]");
            TestReporter.assertAll();
        } else {
            TestReporter.softAssertEquals(search.getTravelComponentGroupingId(String.valueOf(1)), book.getTravelComponentGroupingId(), "Validate TcgID from the reservartion: [" + book.getTravelComponentGroupingId() + "] matches the "
                    + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId(String.valueOf(1)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentId(String.valueOf(1)), book.getTravelComponentId(), "Validate TcID from the reservartion: [" + book.getTravelComponentId() + "] matches the "
                    + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId(String.valueOf(1)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentGroupingId(String.valueOf(2)), tcgId, "Validate TcgID from the reservartion: [" + tcgId + "] matches the "
                    + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId(String.valueOf(2)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentId(String.valueOf(2)), tcId, "Validate TcID from the reservartion: [" + tcId + "] matches the "
                    + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId(String.valueOf(2)) + "]");
            TestReporter.assertAll();
        }

    }

    public void matchReservationInfoWithResponseInfo(SearchAccommodationsForShare search, ReplaceAllForTravelPlanSegment book, int count) {

        for (int i = 1; i <= count; i++) {
            TestReporter.softAssertEquals(search.getResortCode(String.valueOf(i)), book.getResortCode(), "Validate resort code from the reservartion: [" + book.getResortCode() + "] matches the "
                    + "resort code from the SearchAccommodationsForShare response: [" + search.getResortCode(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getRoomTypeCode(String.valueOf(i)), book.getRoomTypeCode(), "Validate room type code from the reservartion: [" + book.getRoomTypeCode() + "] matches the "
                    + "room type code from the SearchAccommodationsForShare response: [" + search.getRoomTypeCode(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelPlanSegmentId(String.valueOf(i)), book.getTravelPlanSegmentId(), "Validate TpsID from the reservartion: [" + book.getTravelPlanSegmentId() + "] matches the "
                    + "TpsID from the SearchAccommodationsForShare response: [" + search.getTravelPlanSegmentId(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getFirstName(String.valueOf(i)), book.getFirstName(), "Validate guest first name from the reservartion: [" + book.getFirstName() + "] matches the "
                    + "guest first name from the SearchAccommodationsForShare response: [" + search.getFirstName(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getLastName(String.valueOf(i)), book.getLastName(), "Validate guest last name from the reservartion: [" + book.getLastName() + "] matches the "
                    + "guest last name from the SearchAccommodationsForShare response: [" + search.getLastName(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelStatus(String.valueOf(i)), book.getTravelStatus(), "Validate travel status from the reservartion: [" + book.getTravelStatus() + "] matches the "
                    + "travel status from the SearchAccommodationsForShare response: [" + search.getTravelStatus(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getEndDate(String.valueOf(i)), book.getEndDate(), "Validate end date from the reservartion: [" + book.getEndDate() + "] matches the "
                    + "end date from the SearchAccommodationsForShare response: [" + search.getEndDate(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getStartDate(String.valueOf(i)), book.getStartDate(), "Validate start date from the reservartion: [" + book.getStartDate() + "] matches the "
                    + "start date from the SearchAccommodationsForShare response: [" + search.getStartDate(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentGroupingId(String.valueOf(i)), book.getTravelComponentGroupingId(), "Validate TcgID from the reservartion: [" + book.getTravelComponentGroupingId() + "] matches the "
                    + "TcgID from the SearchAccommodationsForShare response: [" + search.getTravelComponentGroupingId(String.valueOf(i)) + "]");
            TestReporter.softAssertEquals(search.getTravelComponentId(String.valueOf(i)), book.getTravelComponentId(), "Validate TcID from the reservartion: [" + book.getTravelComponentId() + "] matches the "
                    + "TcID from the SearchAccommodationsForShare response: [" + search.getTravelComponentId(String.valueOf(i)) + "]");
            TestReporter.assertAll();
        }

    }

    public void validateReturnNodeCount(SearchAccommodationsForShare search, int expectedCount) {

        int count = search.getNumberOfResponseNodesByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return");

        if (count == expectedCount) {
            TestReporter.assertEquals(count, expectedCount, "Verify return node count is as expected.  Count: [" + count + "]");
        } else {
            TestReporter.assertTrue(count == expectedCount, "Return node count: [" + count + "] differs from expected count: [" + expectedCount + "]");
        }
    }

    public void validateReturnNodeCountOneOrGreater(SearchAccommodationsForShare search, ReplaceAllForTravelPlanSegment book) {

        int count = search.getNumberOfResponseNodesByXPath("/Envelope/Body/searchAccommodationsForShareResponse/return");

        TestReporter.assertTrue(count >= 1, "Return node count: [" + count + "] is greater than or equal to 1");

        if (count >= 50) {
            for (int i = 1; i <= count; i++) {
                TestReporter.softAssertEquals(search.getResortCode(String.valueOf(i)), book.getResortCode(), "Validate resort code from the reservartion: [" + book.getResortCode() + "] matches the "
                        + "resort code from the SearchAccommodationsForShare response: [" + search.getResortCode(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getRoomTypeCode(String.valueOf(i)), book.getRoomTypeCode(), "Validate room type code from the reservartion: [" + book.getRoomTypeCode() + "] matches the "
                        + "room type code from the SearchAccommodationsForShare response: [" + search.getRoomTypeCode(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getTravelPlanSegmentId(String.valueOf(i)), book.getTravelPlanSegmentId(), "Validate TpsID from the reservartion: [" + book.getTravelPlanSegmentId() + "] matches the "
                        + "TpsID from the SearchAccommodationsForShare response: [" + search.getTravelPlanSegmentId(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getFirstName(String.valueOf(i)), book.getFirstName(), "Validate guest first name from the reservartion: [" + book.getFirstName() + "] matches the "
                        + "guest first name from the SearchAccommodationsForShare response: [" + search.getFirstName(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getLastName(String.valueOf(i)), book.getLastName(), "Validate guest last name from the reservartion: [" + book.getLastName() + "] matches the "
                        + "guest last name from the SearchAccommodationsForShare response: [" + search.getLastName(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getTravelStatus(String.valueOf(i)), book.getTravelStatus(), "Validate travel status from the reservartion: [" + book.getTravelStatus() + "] matches the "
                        + "travel status from the SearchAccommodationsForShare response: [" + search.getTravelStatus(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getEndDate(String.valueOf(i)), book.getEndDate(), "Validate end date from the reservartion: [" + book.getEndDate() + "] matches the "
                        + "end date from the SearchAccommodationsForShare response: [" + search.getEndDate(String.valueOf(i)) + "]");
                TestReporter.softAssertEquals(search.getStartDate(String.valueOf(i)), book.getStartDate(), "Validate start date from the reservartion: [" + book.getStartDate() + "] matches the "
                        + "start date from the SearchAccommodationsForShare response: [" + search.getStartDate(String.valueOf(i)) + "]");
                TestReporter.assertAll();
            }
        }
        if (count < 50) {
            for (int i = 1; i <= count; i++) {
                if (search.getTravelComponentGroupingId(String.valueOf(i)).equals(book.getTravelComponentGroupingId())) {
                    flag = i;
                }
            }
            TestReporter.softAssertEquals(search.getResortCode(String.valueOf(flag)), book.getResortCode(), "Validate resort code from the reservartion: [" + book.getResortCode() + "] matches the "
                    + "resort code from the SearchAccommodationsForShare response: [" + search.getResortCode(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getRoomTypeCode(String.valueOf(flag)), book.getRoomTypeCode(), "Validate room type code from the reservartion: [" + book.getRoomTypeCode() + "] matches the "
                    + "room type code from the SearchAccommodationsForShare response: [" + search.getRoomTypeCode(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getTravelPlanSegmentId(String.valueOf(flag)), book.getTravelPlanSegmentId(), "Validate TpsID from the reservartion: [" + book.getTravelPlanSegmentId() + "] matches the "
                    + "TpsID from the SearchAccommodationsForShare response: [" + search.getTravelPlanSegmentId(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getFirstName(String.valueOf(flag)), book.getFirstName(), "Validate guest first name from the reservartion: [" + book.getFirstName() + "] matches the "
                    + "guest first name from the SearchAccommodationsForShare response: [" + search.getFirstName(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getLastName(String.valueOf(flag)), book.getLastName(), "Validate guest last name from the reservartion: [" + book.getLastName() + "] matches the "
                    + "guest last name from the SearchAccommodationsForShare response: [" + search.getLastName(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getTravelStatus(String.valueOf(flag)), book.getTravelStatus(), "Validate travel status from the reservartion: [" + book.getTravelStatus() + "] matches the "
                    + "travel status from the SearchAccommodationsForShare response: [" + search.getTravelStatus(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getEndDate(String.valueOf(flag)), book.getEndDate(), "Validate end date from the reservartion: [" + book.getEndDate() + "] matches the "
                    + "end date from the SearchAccommodationsForShare response: [" + search.getEndDate(String.valueOf(flag)) + "]");
            TestReporter.softAssertEquals(search.getStartDate(String.valueOf(flag)), book.getStartDate(), "Validate start date from the reservartion: [" + book.getStartDate() + "] matches the "
                    + "start date from the SearchAccommodationsForShare response: [" + search.getStartDate(String.valueOf(flag)) + "]");
            TestReporter.assertAll();
        }
    }
}
