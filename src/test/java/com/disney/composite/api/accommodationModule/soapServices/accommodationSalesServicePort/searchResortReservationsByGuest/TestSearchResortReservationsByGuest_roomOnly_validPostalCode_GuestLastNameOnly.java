package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.searchResortReservationsByGuest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchResortReservationsByGuest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchResortReservationsByGuest_roomOnly_validPostalCode_GuestLastNameOnly extends AccommodationBaseTest {

    // private ReplaceAllForTravelPlanSegment book;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        createHouseHold();
        getHouseHold().primaryGuest().primaryAddress().setCity("Winston Salem");
        getHouseHold().primaryGuest().primaryAddress().setState("North Carolina");
        getHouseHold().primaryGuest().primaryAddress().setStateAbbv("NC");
        getHouseHold().primaryGuest().primaryAddress().setZipCode("27127");
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();
        // getHouseHold().sendToApi(getEnvironment());
        // System.out.println(getBook().getTravelPlanSegmentId());
        // System.out.print(getBook().getRequest());
        // System.out.println(getBook().getResponse());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest", "smoke" })
    public void testSearchResortReservationsByGuest_roomOnly_validPostalCode_GuestLastNameOnly() {

        Guest guest = getHouseHold().primaryGuest();

        // Guest addresses = getHouseHold().primaryGuest();
        // System.out.print(getBook().getRequest());
        TestReporter.logScenario("Test - SearchResortReservationsByGuest - Room Only -validPostalCode-GuestLastNameOnly");

        SearchResortReservationsByGuest searchRRByGuest = new SearchResortReservationsByGuest(environment);
        searchRRByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestLastName(guest.getLastName());
        searchRRByGuest.setGuestFirstName(guest.getFirstName());
        searchRRByGuest.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());

        searchRRByGuest.setPostalCode(guest.primaryAddress().getZipCode());
        searchRRByGuest.setArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.sendRequest();

        // System.out.print(searchRRByGuest.getRequest());
        // System.out.println(searchRRByGuest.getResponse());
        TestReporter.logAPI(!searchRRByGuest.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", searchRRByGuest);

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
                    TestReporter.assertNotNull(searchRRByGuest.getResortCode(), "The Resort Code at index [" + index + "] has a Resort code of " + searchRRByGuest.getResortCode() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortStartDate(), "The Resort Start Date at index [" + index + "] has a Resort Start date of " + searchRRByGuest.getResortStartDate() + ".");
                    TestReporter.assertNotNull(searchRRByGuest.getResortEndDate(), "The Resort End Date at index [" + index + "] has a Resort End date of " + searchRRByGuest.getResortEndDate() + ".");
                    TestReporter.assertAll();
                } else if (reservationStatus.equals("CHECKED-IN")) {
                    TestReporter.assertTrue(reservationStatus.equals("CHECKED-IN"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");

                } else if (reservationStatus.equals("CHECKED-OUT")) {
                    TestReporter.assertTrue(reservationStatus.equals("CHECKED-OUT"), "The Reservation at index [" + index + "] has a Reservation Status of " + reservationStatus + ".");
                }

            }
        }

        // old vs. new not valid here

    }
}
