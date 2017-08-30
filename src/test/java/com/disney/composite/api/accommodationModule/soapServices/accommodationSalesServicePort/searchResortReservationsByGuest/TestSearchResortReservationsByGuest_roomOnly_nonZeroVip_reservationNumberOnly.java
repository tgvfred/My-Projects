package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.searchResortReservationsByGuest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchResortReservationsByGuest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestSearchResortReservationsByGuest_roomOnly_nonZeroVip_reservationNumberOnly extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment book;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setNonZeroVip(true);
        setValues(environment);

        bookReservation();

        book = new ReplaceAllForTravelPlanSegment(environment, "book1Adult1Child");

        book.sendRequest();
        book.getResponse();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuestName", "smoke" })
    public void testSearchResortReservationsByGuest_roomOnly_nonZeroVip_reservationNumberOnly() {
        TestReporter.logScenario("Test - SearchResortReservationsByGuest - Room Only -nonZeroVip- reservationNumberOnly");

        SearchResortReservationsByGuest searchRRByGuest = new SearchResortReservationsByGuest(environment);
        searchRRByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        // searchRRByGuest.setReservationNumber("463551872146");
        searchRRByGuest.setReservationNumber(getBook().getTravelPlanSegmentId());
        searchRRByGuest.setPostalCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.sendRequest();
        TestReporter.logAPI(!searchRRByGuest.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", searchRRByGuest);
        System.out.println(searchRRByGuest.getResponse());
        int numberOfResortReservations;

        String reservationStatus;
        String vipLevel;
        numberOfResortReservations = searchRRByGuest.getNumberOfResponseNodesByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations");

        // Validate the vipLevel is not '0'
        vipLevel = searchRRByGuest.getResponseNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/vipLevel");

        if (!vipLevel.equals("0")) {
            TestReporter.assertTrue(!vipLevel.equals("0"), "The VIP Level is [" + vipLevel + "] and is not 0.");

        } else {
            TestReporter.logFailure("VIP Level is a 0");
        }

        // Validate the returned reservations are either BOOKED, CHECKED-IN, or CHECKED-OUT on the current date
        // If number of results is > 150, the service should throw an error. If < 150, verify that the booked reservation is found in the response Validate the data for the prereq reservation

        if (numberOfResortReservations > 150) {
            TestReporter.assertTrue(numberOfResortReservations < 150, "Error the Response returned more than 150 results. Number of Results: " + numberOfResortReservations);

        } else {

            for (int index = 1; index <= numberOfResortReservations; index++) {
                reservationStatus = searchRRByGuest.getReservationStatus();
                if (reservationStatus.equals("Booked")) {

                    TestReporter.assertTrue(reservationStatus.equals("Booked"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");
                    TestReporter.assertEquals(searchRRByGuest.getReservationNumber(), getBook().getTravelPlanSegmentId(), "The Reservation Number for the response [" + searchRRByGuest.getReservationNumber() + "] matches the Reservation number in the request " + getBook().getTravelPlanSegmentId() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortCode(), "The Resort Code at index [" + index + "] has a Resort code of " + searchRRByGuest.getResortCode() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortStartDate(), "The Resort Start Date at index [" + index + "] has a Resort Start date of " + searchRRByGuest.getResortStartDate() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortEndDate(), "The Resort End Date at index [" + index + "] has a Resort End date of " + searchRRByGuest.getResortEndDate() + ".");

                    TestReporter.assertAll();

                } else if (reservationStatus.equals("CHECKED-IN")) {
                    TestReporter.assertTrue(reservationStatus.equals("CHECKED-IN"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");

                } else if (reservationStatus.equals("CHECKED-OUT")) {
                    TestReporter.assertTrue(reservationStatus.equals("CHECKED-OUT"), "The Reservation at index [" + index + "] has a Reservation Status of  " + reservationStatus + ".");
                }

            }
        }
        // old vs. new

        if (Environment.isSpecialEnvironment(getEnvironment())) {

            SearchResortReservationsByGuest clone = (SearchResortReservationsByGuest) searchRRByGuest.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (searchRRByGuest.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/role");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(searchRRByGuest, true), "Validating Response Comparison");

            // }

        }

    }
}
