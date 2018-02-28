package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_shareAccommodations_emptyRoomReservationDetail_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_emptyRoomReservationDetail_Negative() {

        ShareAccommodations share = new ShareAccommodations(environment);

        // share.setAccommodation(BaseSoapCommands.REMOVE_NODE.toString());
        share.setBookingDate("2017-10-25T00:00:00");
        share.setFreezeId("0");
        share.setGuaranteeStatus("None");
        share.setInventoryStatus("Unassigned");
        share.setOverrideFreeze("false");
        share.setPackageCode(getPackageCode());
        share.setResortCode(getResortCode());
        share.setResortEndDate(getBook().getEndDate());
        share.setResortStartDate(getBook().getStartDate());
        share.setRoomTypeCode(getRoomTypeCode());
        share.setRSRReservations("false");
        share.setTcgId(getBook().getTravelComponentGroupingId());
        share.setTcId(getBook().getTravelComponentId());
        share.setTravelStatus("Booked");
        share.setLocationId(getLocationId());

        share.setShared("false");
        share.setSpecialNeedsRequested("0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/blockCode", BaseSoapCommands.REMOVE_NODE.toString());
        //
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/checkOutDateTime", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/externalReferences", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/fplosId", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/InventoryTrackingId", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/modificationDate", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateCategoryCode", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomNumber", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/age", getAge());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/ageType", getAgeType());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/title", getHouseHold().primaryGuest().getTitle());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/middleName", getHouseHold().primaryGuest().getMiddleName());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/partyId", getPartyId());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/locatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/guestLocatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/locatorUseType", "HOUSEHOLD");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/primary", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/deviceType", "HANDSET");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/extension", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/locatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/guestLocatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/primary", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", getHouseHold().primaryGuest().primaryAddress().getCountry());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName", getHouseHold().primaryGuest().primaryAddress().getStateAbbv());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/locatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/guestLocatorId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/primary", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage", "Englist");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/guestId", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/active", "true");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/dob", getHouseHold().primaryGuest().getBirthDate());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/purposeOfVisit", "Leisure");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/role", "Guest");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/correlationID", "0");
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/experienceMediaDetails/id", "0");
        //
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/salesChannelId", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/sourceExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/teamName", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketGroup", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketDetails", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations//inventoryOverrideReason", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/inventoryOverrideContactName", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/componentDetail", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/exchangeFee", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/reservationType", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/pointsPaymentReferences", BaseSoapCommands.REMOVE_NODE.toString());
        // share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/offerIdentifiers", BaseSoapCommands.REMOVE_NODE.toString());

        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second reservation: " + share.getFaultString(), share);

        // validateApplicationError(share, MISSING_REQUIRED_PARAM_EXCEPTION);

    }
}
