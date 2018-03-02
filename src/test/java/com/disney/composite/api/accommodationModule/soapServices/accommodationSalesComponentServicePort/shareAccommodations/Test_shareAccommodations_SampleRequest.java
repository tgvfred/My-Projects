package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_shareAccommodations_SampleRequest extends AccommodationBaseTest {
    private Share share;
    private String firstOwnerId;
    private String secondOwnerId;
    private String firstTCG;
    private String firstTC;
    private String firstTPS;
    private String firstRC;
    private String firstRS;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        setEnvironment(environment);
        isComo.set("false");

        daysOut.set(0);

        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        firstTCG = getBook().getTravelComponentGroupingId();
        firstTC = getBook().getTravelComponentId();
        firstTPS = getBook().getTravelPlanSegmentId();
        firstRC = getBook().getRoomTypeCode();
        firstRS = getBook().getResortCode();
        // System.out.println(getBook().getResponse());
        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_SampleRQ() {

        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(firstTCG);
        // retrieveRates.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);

        System.out.println(retrieveRates.getResponse());

        ShareAccommodations share = new ShareAccommodations(environment);

        share.setBookingDate(getBook().getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/bookingDate"));
        share.setFreezeId("0");
        share.setGuaranteeStatus("None");
        share.setInventoryStatus("Unassigned");
        share.setOverrideFreeze("false");
        share.setPackageCode(getPackageCode());
        share.setResortCode(firstRS);
        share.setResortEndDate(getBook().getEndDate());
        share.setResortStartDate(getBook().getStartDate());
        share.setRoomTypeCode(firstRC);
        share.setRSRReservations("false");
        share.setTcgId(firstTCG);
        share.setTcId(firstTC);
        share.setTravelStatus("Booked");
        share.setLocationId(getLocationId());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/shared", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/specialNeedsRequested", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/blockCode", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/checkOutDateTime", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/externalReferences", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/fplosId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/InventoryTrackingId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/modificationDate", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateCategoryCode", BaseSoapCommands.REMOVE_NODE.toString());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/additionalCharge", retrieveRates.getAdditionalCharge());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/additionalChargeOverridden", "false");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/basePrice", retrieveRates.getBasePrice());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/rackRate", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/date", getBook().getStartDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/dayCount", retrieveRates.getDayCount());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/overidden", retrieveRates.getRateDetailsOveridden());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/shared", retrieveRates.getShared());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/netPrice", "0.0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/rateDetails/pointsValue", "0");

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomNumber", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/comments", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/suffix", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/age", "20");

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/salesChannelId", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/sourceExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/teamName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketGroup", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/ticketDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations//inventoryOverrideReason", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/inventoryOverrideContactName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/componentDetail", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/exchangeFee", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/reservationType", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/pointsPaymentReferences", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/offerIdentifiers", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/ageType", "ADULT");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/title", getHouseHold().primaryGuest().getTitle());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/addressDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/emailDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/phoneDetails", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/membershipDetail", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/profiles", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/experienceMediaDetails/mediaCustomization", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/roomModificationContactName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/roomModificationReason", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/additionalOccupants", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/correlationID", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/experienceMediaDetails/id", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/dclTransferCode", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/guestTypeName", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/favouriteCharacter", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/guestIdReferences", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/gender", BaseSoapCommands.REMOVE_NODE.toString());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/middleName", getHouseHold().primaryGuest().getMiddleName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/partyId", getPartyId());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage", "English");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/guestId", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/active", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/guest/dob", getHouseHold().primaryGuest().getBirthDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations/roomReservationDetail/guestReferenceDetails/purposeOfVisit", "Leisure");

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request", BaseSoapCommands.ADD_NODE.commandAppend("accommodations"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("bookingDate"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/bookingDate", getBook().getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/bookingDate"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("guaranteeStatus"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/guaranteeStatus", "None");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("overideFreeze"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/overideFreeze", "False");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("packageCode"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/packageCode", getPackageCode());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("resortCode"));

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("rateDetails"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("additionalCharge"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/additionalCharge", "0.0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("additionalChargeOverridden"));

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/additionalChargeOverridden", "false");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("basePrice"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/basePrice", retrieveRates.getBasePrice());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("date"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/date", getBook().getStartDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("dayCount"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/dayCount", retrieveRates.getDayCount());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("overidden"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/overidden", retrieveRates.getRateDetailsOveridden());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("shared"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/shared", retrieveRates.getShared());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("netPrice"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/netPrice", "0.0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails", BaseSoapCommands.ADD_NODE.commandAppend("pointsValue"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rateDetails/pointsValue", "0");

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/resortCode", getBook().getResortCode());

        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("resortPeriod"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/resortPeriod", BaseSoapCommands.ADD_NODE.commandAppend("endDate"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/resortPeriod/endDate", getBook().getEndDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/resortPeriod", BaseSoapCommands.ADD_NODE.commandAppend("startDate"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/resortPeriod/startDate", getBook().getStartDate());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("roomReservationDetail"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail", BaseSoapCommands.ADD_NODE.commandAppend("guestReferenceDetails"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("age"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/age", "20");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/ageType", "ADULT");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("title"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/title", getHouseHold().primaryGuest().getTitle());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/middleName", getHouseHold().primaryGuest().getMiddleName());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/partyId", getHouseHold().primaryGuest().getPartyId());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/doNotMailIndicator", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/doNotPhoneIndicator", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage", "English");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/dclGuestId", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/guestId", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest", BaseSoapCommands.ADD_NODE.commandAppend("active"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/guest/active", "true");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("purposeOfVisit"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/purposeOfVisit", "Leisure");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("role"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/role", "Guest");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomReservationDetail/guestReferenceDetails/correlationID", "0");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("roomTypeCode"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/roomTypeCode", getRoomTypeCode());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("rsrReservation"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/rsrReservation", "false");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/travelComponentGroupingId", getBook().getTravelComponentGroupingId());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentId"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/travelComponentId", getBook().getTravelComponentId());
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("travelStatus"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/travelStatus", "Booked");
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]", BaseSoapCommands.ADD_NODE.commandAppend("shared"));
        share.setRequestNodeValueByXPath("Envelope/Body/shareAccommodations/request/accommodations[2]/shared", "true");
        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"),
                "An error occurred share reservation: " + share.getFaultString(), share);

    }
}