package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import org.testng.annotations.AfterMethod;
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
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_shareAccommodations_twoTcgs extends AccommodationBaseTest {
    private Share share;
    private String firstOwnerId;
    private String secondOwnerId;
    private String firstTCG;
    private String firstTC;
    private String firstTPS;
    private String firstRC;
    private String firstRS;
    Recordset rs5;

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
    public void test_shareAccommodations_twoTcgs() {

        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(firstTCG);

        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);

        // System.out.println(retrieveRates.getResponse());

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
        share.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
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

        TestReporter.logStep("Validate data in the response node.");

        String bookingDate = share.getBookingDate();
        String travelStatus = share.getTravelStatus();

        TestReporter.softAssertTrue(!share.getTcgIndex1().equals(share.getTcgIndex2()), "Verify that the response returns the first tcgId [" + share.getTcgIndex1() + "] and the second tcgID [" + share.getTcgIndex2() + "].");
        TestReporter.softAssertTrue(!share.getTcIndex1().equals(share.getTcIndex2()), "Verify that the response returns the first tcId [" + share.getTcgIndex1() + "] and the second tcID [" + share.getTcgIndex2() + "].");

        TestReporter.softAssertEquals(Randomness.generateCurrentXMLDate(), bookingDate.substring(0, 10), "Verify that the booking date [" + Randomness.generateCurrentXMLDate() + "] that which is expected [" + bookingDate.substring(0, 10) + "].");
        TestReporter.softAssertEquals(travelStatus, "Booked", "Verify that the response returns the travel status [" + travelStatus + "] that which is expected [Booked].");

        String sql = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + firstTCG + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No owner id found for tcg ID [ " + firstTCG + " ]", sql);
        }

        firstOwnerId = rs.getValue("ASGN_OWN_ID");

        String sql2 = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";
        Database db2 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs2 = new Recordset(db2.getResultSet(sql2));

        if (rs2.getRowCount() == 0) {
            throw new SQLValidationException("No owner id found for tcg ID [ " + getBook().getTravelComponentGroupingId() + " ]", sql2);
        }

        secondOwnerId = rs2.getValue("ASGN_OWN_ID");

        TestReporter.assertAll();
        TestReporter.logStep("Verify assignment owner id is the same for both TCG's but is not the same as either original ownder Id for each TCG");

        String sql3 = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + firstTCG + "'";
        Database db3 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs3 = new Recordset(db3.getResultSet(sql3));

        if (rs3.getRowCount() == 0) {
            throw new SQLValidationException("No assignment owner id found for tcg ID [ " + firstTCG + " ]", sql);
        }

        String assignFirstOwnerId = rs3.getValue("ASGN_OWN_ID");

        String sql4 = "select a.* from res_mgmt.tc a join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";
        Database db4 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs4 = new Recordset(db4.getResultSet(sql4));

        String assignSecondOwnerId = rs4.getValue("ASGN_OWN_ID");

        do {
            TestReporter.softAssertEquals(assignFirstOwnerId, assignSecondOwnerId, "Verify the assignment owner Ids for each TCG [" + assignFirstOwnerId + "] match [" + assignSecondOwnerId + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.logStep("Verify reservation history");
        String sql5 = "select * from res_mgmt.res_hist a where a.tps_id = '" + getBook().getTravelPlanSegmentId() + "'";
        Database db5 = new OracleDatabase(environment, Database.DREAMS);
        rs5 = new Recordset(db5.getResultSet(sql5));

        if (rs5.getRowCount() == 0) {
            throw new SQLValidationException("No reservation found for tps ID [ " + getBook().getTravelPlanSegmentId() + " ]", sql5);
        }

        for (int i = 1; i <= rs5.getRowCount(); i++) {
            if (rs5.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
                TestReporter.assertEquals(rs5.getValue("RES_HIST_PROC_DS", i), "Shared", "Verify the reservation history status [" + rs5.getValue("RES_HIST_PROC_DS", i) + "] matches the reservation history status in the DB [Shared]");
            }
            TestReporter.assertAll();
        }
        TestReporter.logStep("Verify share in flag");

        String sql6 = "select c.* from res_mgmt.tc_grp a join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb join res_mgmt.acm_cmpnt c on b.tc_id = c.acm_tc_id where a.tc_grp_nb = '" + firstTCG + "'";
        Database db6 = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs6 = new Recordset(db6.getResultSet(sql6));

        if (rs6.getRowCount() == 0) {
            throw new SQLValidationException("No share flag found for tcg ID [ " + firstTCG + " ]", sql6);
        }

        for (int i = 1; i <= rs6.getRowCount(); i++) {
            // if (rs5.getValue("RES_HIST_PROC_DS", i).equals("Shared")) {
            TestReporter.assertEquals(rs6.getValue("SHR_IN", i), "Y", "Verify the room has been shared [" + rs6.getValue("SHR_IN", i) + "] in the DB [Y]");
            TestReporter.assertAll();
            // }

        }
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(firstTCG);
            cancel(getBook().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
    }
}