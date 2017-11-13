package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.searchResortReservationsByGuest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchResortReservationsByGuest;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchResortReservationsByGuest_roomOnly_validResortCode_ArrivalDate_LastNameOnly extends AccommodationBaseTest {

    private Boolean addGuest;

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
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();
    }

    // test
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest" })
    public void testSearchResortReservationsByGuest_roomOnly_validResortCode_ArrivalDate_LastNameOnly() {
        // DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        // Date localDate = new Date();
        //
        // String arrivalDate = dtf.format(localDate);
        // String baseXpath = "/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest/guestLastName";
        Guest guest = getHouseHold().primaryGuest();

        TestReporter.logScenario("Test - SearchResortReservationsByGuest - Room Only -validResortCode- ArrivalDate- LastNameOnly");

        SearchResortReservationsByGuest searchRRByGuest = new SearchResortReservationsByGuest(environment);
        searchRRByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setGuestLastName(guest.getLastName());
        searchRRByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setPostalCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchRRByGuest.setArrivalDate(getArrivalDate());
        // searchRRByGuest.setArrivalDate(arrivalDate);
        searchRRByGuest.setResortCode(getResortCode());
        searchRRByGuest.sendRequest();
        // System.out.print(searchRRByGuest.getResponse());
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

                    // TestReporter.assertEquals(searchRRByGuest.getGuestLastName(), guest.getLastName(), "The Last Name for the response [" +
                    // searchRRByGuest.getGuestLastName() + "] matches the request [" + guest.getLastName() + "]");

                    TestReporter.assertEquals(searchRRByGuest.getResortStartDate(), getArrivalDate(), "The Arrival Date for the response [" + searchRRByGuest.getResortStartDate() + "] matches the request date [" + getArrivalDate() + "]");
                    TestReporter.assertEquals(searchRRByGuest.getResortCode(), getResortCode(), "The Resort Code for the response  [" + searchRRByGuest.getResortCode() + "] matches the request resort code [" + getResortCode() + "]");
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
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts");
            // clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2705382']");
            // clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2708375']");
            // clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2856383']");
            // clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/couponProducts[text()='2856382']");
            // clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/partyRoles/role");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/travelPlanSegmentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/travelPlanId");
            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/travelPlanId");
            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/contactName");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/contactName");
            clone.addExcludedXpathValidations("/Envelope/Body/searchResortReservationsByGuestResponse/resortReservations/travelPlanSegmentId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(searchRRByGuest, true), "Validating Response Comparison");

            // }

        }
    }
}
