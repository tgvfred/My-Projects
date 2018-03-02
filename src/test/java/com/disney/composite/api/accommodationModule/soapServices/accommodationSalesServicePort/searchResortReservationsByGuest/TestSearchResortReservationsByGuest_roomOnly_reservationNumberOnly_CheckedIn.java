package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.searchResortReservationsByGuest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchResortReservationsByGuest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestSearchResortReservationsByGuest_roomOnly_reservationNumberOnly_CheckedIn extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment book;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        getAddTravelAgency();
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

        // book = new ReplaceAllForTravelPlanSegment(environment, "book2AdultsAndTwoRoom");
        //
        // book.sendRequest();
        // book.getResponse();

        // checkingIn(environment);

    }

    // test
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest" })
    public void testSearchResortReservationsByGuest_roomOnly_reservationNumberOnly_CheckedIn() {
        TestReporter.logScenario("Test - SearchResortReservationsByGuest - Room Only -reservationNumberOnly-CheckedIn");

        CheckIn checkIn = new CheckIn(environment, "Main");
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkIn.setGuestId(getBook().getGuestId());
        checkIn.sendRequest();

        TestReporter.logAPI(!checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred while checking in a reservation " + checkIn.getFaultString(), checkIn);
        SearchResortReservationsByGuest searchRRByGuest = new SearchResortReservationsByGuest(environment);
        searchRRByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        // searchRRByGuest.setReservationNumber("472401660157");
        searchRRByGuest.setReservationNumber(getBook().getTravelPlanSegmentId());
        searchRRByGuest.setPostalCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.sendRequest();
        TestReporter.logAPI(!searchRRByGuest.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", searchRRByGuest);

        // System.out.print(searchRRByGuest.getResponse());
        int numberOfResortReservations;

        String reservationStatus;

        numberOfResortReservations = searchRRByGuest.getNumberOfResponseNodesByXPath("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations");

        // Validate the returned reservations are either BOOKED, CHECKED-IN, or CHECKED-OUT on the current date
        // If number of results is > 150, the service should throw an error. If < 150, verify that the booked reservation is found in the response Validate the
        // data for the prereq reservation

        if (numberOfResortReservations > 150) {
            TestReporter.assertTrue(numberOfResortReservations < 150, "Error the Response returned more than 150 results. Number of Results: " + numberOfResortReservations);

        } else {

            for (int index = 1; index <= numberOfResortReservations; index++) {
                reservationStatus = searchRRByGuest.getReservationStatus();
                if (reservationStatus.equals("Booked")) {

                    TestReporter.assertTrue(reservationStatus.equals("Booked"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");

                    // TestReporter.assertEquals(searchRRByGuest.getReservationNumber(), "472071512745", "The Reservation Number for the response [" +
                    // searchRRByGuest.getReservationNumber() + "] matches the Reservation Number request number [472071512745].");
                    TestReporter.assertEquals(searchRRByGuest.getReservationNumber(), getBook().getTravelPlanSegmentId(), "The Reservation Number for the response [" + searchRRByGuest.getReservationNumber() + "] matches the  Reservation Number request number [" + getBook().getTravelPlanSegmentId() + "].");
                    TestReporter.assertNotNull(searchRRByGuest.getResortCode(), "The Resort Code at index [" + index + "] has a Resort code of " + searchRRByGuest.getResortCode() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortStartDate(), "The Resort Start Date at index [" + index + "] has a Resort Start date of " + searchRRByGuest.getResortStartDate() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortEndDate(), "The Resort End Date at index [" + index + "] has a Resort End date of " + searchRRByGuest.getResortEndDate() + ".");
                    TestReporter.assertAll();
                } else if (reservationStatus.equals("Checked In") || reservationStatus.equals("Checking In")) {

                    TestReporter.assertTrue(reservationStatus.equals("Checked In") || reservationStatus.equals("Checking In"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");
                    TestReporter.assertEquals(searchRRByGuest.getReservationNumber(), getBook().getTravelPlanSegmentId(), "The Reservation Number for the response [" + searchRRByGuest.getReservationNumber() + "] matches the  Reservation Number request number [" + getBook().getTravelPlanSegmentId() + "].");
                    TestReporter.assertNotNull(searchRRByGuest.getResortCode(), "The Resort Code at index [" + index + "] has a Resort code of " + searchRRByGuest.getResortCode() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortStartDate(), "The Resort Start Date at index [" + index + "] has a Resort Start date of " + searchRRByGuest.getResortStartDate() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortEndDate(), "The Resort End Date at index [" + index + "] has a Resort End date of " + searchRRByGuest.getResortEndDate() + ".");
                    // TestReporter.assertNotNull(searchRRByGuest., description);
                    TestReporter.assertAll();
                } else if (reservationStatus.equals("CHECKED-OUT")) {
                    TestReporter.assertTrue(reservationStatus.equals("CHECKED-OUT"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");
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
                // System.out.print(clone.getRequest());
                // System.out.print(clone.getResponse());
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

            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2789372']");
            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2789375']");
            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2789376']");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/role[text()='Guest']");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(searchRRByGuest, true), "Validating Response Comparison");

        }

        // }

    }
}
