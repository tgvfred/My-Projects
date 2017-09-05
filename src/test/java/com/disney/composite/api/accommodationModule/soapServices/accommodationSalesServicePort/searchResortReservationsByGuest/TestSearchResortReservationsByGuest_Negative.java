package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.searchResortReservationsByGuest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchResortReservationsByGuest;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestSearchResortReservationsByGuest_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest", "negative" })
    public void testSearchResortReservationsByGuest_nullRequest() {
        String fault = "Invalid Search Criteria  : Search Request Not Valid !";
        TestReporter.logScenario("Test - Search Resort Reservations By Guest - Null Request");

        SearchResortReservationsByGuest searchResortReservationsByGuest = new SearchResortReservationsByGuest(environment);
        searchResortReservationsByGuest.setRequestNodeValueByXPath("/Envelope/Body/searchResortReservationsByGuest/searchResortReservationsRequest", BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.sendRequest();

        TestReporter.logAPI(!searchResortReservationsByGuest.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + searchResortReservationsByGuest.getFaultString() + " ]", searchResortReservationsByGuest);
        validateApplicationError(searchResortReservationsByGuest, AccommodationErrorCode.INVALID_SEARCH_CRITERIA);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest", "negative" })
    public void testSearchResortReservationsByGuest_invalidPostalCode_GuestLastNameOnly() {
        String lastName = "ASD";
        String postalCode = "123456789";
        String fault = "Invalid Search Criteria  : No Accommodation Components Found !";
        TestReporter.logScenario("Test - Search Resort Reservations By Guest - Invalid Postal Code- Guest Last Name Only");

        SearchResortReservationsByGuest searchResortReservationsByGuest = new SearchResortReservationsByGuest(environment);

        searchResortReservationsByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setGuestLastName(lastName);
        searchResortReservationsByGuest.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setPostalCode(postalCode);
        searchResortReservationsByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.sendRequest();

        TestReporter.logAPI(!searchResortReservationsByGuest.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + searchResortReservationsByGuest.getFaultString() + " ]", searchResortReservationsByGuest);
        validateApplicationError(searchResortReservationsByGuest, AccommodationErrorCode.INVALID_SEARCH_CRITERIA);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest", "negative" })
    public void testSearchResortReservationsByGuest_invalidResortCode_ArrivalDate_LastNameOnly() {

        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate = new Date();

        String lastName = "ASD*";
        String arrivalDate = dtf.format(localDate);

        String fault = "Invalid Search Criteria  : No Accommodation Components Found !";
        TestReporter.logScenario("Test - Search Resort Reservations By Guest - Invalid Resort Code- Arrival Date- Last Name Only");

        SearchResortReservationsByGuest searchResortReservationsByGuest = new SearchResortReservationsByGuest(environment);

        searchResortReservationsByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setGuestLastName(lastName);
        searchResortReservationsByGuest.setReservationNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setPostalCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setArrivalDate(arrivalDate);
        searchResortReservationsByGuest.setResortCode(getResortCode());
        searchResortReservationsByGuest.sendRequest();

        TestReporter.logAPI(!searchResortReservationsByGuest.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + searchResortReservationsByGuest.getFaultString() + " ]", searchResortReservationsByGuest);
        validateApplicationError(searchResortReservationsByGuest, AccommodationErrorCode.INVALID_SEARCH_CRITERIA);

    }

    // test
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "searchResortReservationsByGuest", "negative" })
    public void testSearchResortReservationsByGuest_roomOnly_reservationNumberOnly_CheckedOut() {
        String reservationNumber = "471431814399";
        String fault = "No travel plan data found. : NO RESULTS FOUND";
        TestReporter.logScenario("Test - Search Resort Reservations By Guest - Room Only- Reservation Number Only- Checked Out");

        SearchResortReservationsByGuest searchResortReservationsByGuest = new SearchResortReservationsByGuest(environment);

        searchResortReservationsByGuest.setAgencyIataNumber(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setReservationNumber(reservationNumber);
        searchResortReservationsByGuest.setPostalCode(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        searchResortReservationsByGuest.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());

        searchResortReservationsByGuest.sendRequest();

        TestReporter.logAPI(!searchResortReservationsByGuest.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + searchResortReservationsByGuest.getFaultString() + " ]", searchResortReservationsByGuest);
        validateApplicationError(searchResortReservationsByGuest, AccommodationErrorCode.TRAVEL_PLAN_SEARCH_NO_RESULT);

    }

}
