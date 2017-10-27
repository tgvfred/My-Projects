package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.TestReporter;

public class RetrieveHelper {

    public void baseValidation(ReplaceAllForTravelPlanSegment book, Retrieve retrieve) {

        String guestfirstName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName");

        String guestlastName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName");

        String guestPhone = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");

        String guestAddress = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");

        String guestEmail = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");

        String startPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/startDate");

        String endPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/endDate");

        String guestPartyId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");

        String guestGuestId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");

        // String pfirstName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String plastName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pAddress = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pEmail = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pPartyId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String pGuestId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
        //
        // String roomReadyNotificationInformation = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");

        String travelStatus = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");

        TestReporter.assertEquals(guestfirstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + guestfirstName + "]");
        TestReporter.assertEquals(guestlastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + guestlastName + "]");
        TestReporter.assertEquals(guestPhone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + guestPhone + "]");
        TestReporter.assertEquals(guestAddress, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + guestAddress + "]");
        TestReporter.assertEquals(guestEmail, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + guestEmail + "]");

        TestReporter.assertEquals(startPeriod, retrieve.getPeriodSD(), "Verify the period start date [" + retrieve.getPeriodSD() + "] matches the expected [" + startPeriod + "]");
        TestReporter.assertEquals(endPeriod, retrieve.getPeriodED(), "Verify the period end date [" + retrieve.getPeriodED() + "] matches the expected [" + endPeriod + "]");
        TestReporter.assertNotNull(retrieve.getPartyId(), "Verify the party id is in the response [" + retrieve.getPartyId() + "].");

        TestReporter.assertNotNull(retrieve.getPPFirstName(), "Verify the primary party first name is in response [" + retrieve.getPPFirstName() + "].");
        TestReporter.assertNotNull(retrieve.getPPLastName(), "Verify the primary party last name  is in response [" + retrieve.getPPLastName() + "].");
        TestReporter.assertNotNull(retrieve.getPPPhone(), "Verify the primary party phone is in response [" + retrieve.getPPPhone() + "].");
        TestReporter.assertNotNull(retrieve.getPPAddress(), "Verify the primary party address is in response [" + retrieve.getPPAddress() + "].");
        TestReporter.assertNotNull(retrieve.getPPEmail(), "Verify the primary party email is in response [" + retrieve.getPPEmail() + "].");

        TestReporter.assertNotNull(retrieve.getRoomReadyNotificationInfoTP(), "Verify the room ready notification information travel plan id is in response [" + retrieve.getPartyId() + "].");

        TestReporter.assertNotNull(retrieve.getRoomReadyNotificationInfoRequired(), "Verify the room ready notification information required in response [" + retrieve.getPartyId() + "].");

        TestReporter.assertEquals(guestGuestId, retrieve.getGuestId(), "Verify the guest id [" + retrieve.getGuestId() + "] matches the expected [" + guestGuestId + "]");

        TestReporter.assertEquals(travelStatus, retrieve.getTravelStatus(), "Verify the travel status [" + retrieve.getTravelStatus() + "] matches the expected [" + travelStatus + "]");

    }

}
