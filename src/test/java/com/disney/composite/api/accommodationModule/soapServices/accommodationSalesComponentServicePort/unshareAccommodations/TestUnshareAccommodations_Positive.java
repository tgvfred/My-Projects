package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.unshareAccommodations;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.UnshareAccommodations;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestUnshareAccommodations_Positive extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment firstBooking;
    private ReplaceAllForTravelPlanSegment secondBooking;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1); //
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();
        firstBooking = getBook();

        // book second reservation.
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        isComo.set("true");
        bookReservation();
        secondBooking = getBook();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "unshareAccommodations" })
    public void testUnshareAccommodations_Positive() {

        Share share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstBooking.getTravelComponentGroupingId());
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(secondBooking.getTravelComponentGroupingId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        share.sendRequest();
        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + share.getFaultString(), share);

        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(secondBooking.getTravelComponentGroupingId());

        retrieveRates.sendRequest();
        TestReporter.logAPI(!retrieveRates.getResponseStatusCode().equals("200"), "An error occurred retrieving rates", retrieveRates);

        String firstOverrideFreeze = "false";
        String firstaddChargeOverride = "false";
        String firstDetailsOverridden = "false";
        String firstDetailShared = "false";
        String firstBasePrice = retrieveRates.getBasePrice();
        String firstGuestAge = "49";
        String firstPackageCode = getPackageCode();
        String firstResortCode = getBook().getResortCode();
        String guest1FirstName = "Elijah";
        String guest1LastName = "Holland";
        String firstDontMail = "false";
        String firstDontPhone = "false";
        String firstActive = "false";
        String firstRoomTypeCode = getRoomTypeCode();
        String firstRsrReservation = "false";
        String firstShared = "false";
        String firstGuestPurposeOfVisit = "Leisure";

        String secondOverrideFreeze = "false";
        String secondaddChargeOverride = "false";
        String secondDetailsOverridden = "false";
        String secondDetailShared = "false";
        String secondBasePrice = retrieveRates.getBasePrice();
        String secondGuestAge = "49";
        String secondPackageCode = getPackageCode();
        String secondResortCode = getBook().getResortCode();
        String guest2FirstName = "Elijah";
        String guest2LastName = "Holland";
        String secondDontMail = "false";
        String secondDontPhone = "false";
        String secondActive = "false";
        String secondRoomTypeCode = getRoomTypeCode();
        String secondRsrReservation = "false";
        String secondShared = "false";
        String secondGuestPurposeOfVisit = "Leisure";

        // first unshare
        UnshareAccommodations unshare = new UnshareAccommodations(environment, "Main");

        unshare.setBookingDate("1", date(0));
        unshare.setOverrideFreeze("1", firstOverrideFreeze);
        unshare.setPackageCode("1", firstPackageCode);
        unshare.setResortCode("1", firstResortCode);
        unshare.setAdditionalChargeOverride("1", firstaddChargeOverride);
        unshare.setRateDetailsBasePrice("1", firstBasePrice);
        unshare.setRateDetailsDate("1", date(0));
        unshare.setRateDetailsOverriden("1", firstDetailsOverridden);
        unshare.setRateDetailsShared("1", firstDetailShared);
        unshare.setResortEndDate("1", getDepartureDate());
        unshare.setResortStartDate("1", getArrivalDate());
        unshare.setGuestAge("1", firstGuestAge);
        unshare.setGuestType("1", "ADULT");
        unshare.setGuestFirstName("1", guest1FirstName);
        unshare.setGuestLastName("1", guest1LastName);
        unshare.setGuestMiddleName("1", "Automation");
        unshare.setGuestPartyId("1", "0");
        unshare.setGuestDoNotMailIndicator("1", firstDontMail);
        unshare.setGuestDoNotPhoneIndicator("1", firstDontPhone);
        unshare.setGuestId("1", firstBooking.getGuestId());
        unshare.setGuestDclGuestId("1", "0");
        unshare.setGuestActive("1", firstActive);
        unshare.setGuestPurposeOfVisit("1", firstGuestPurposeOfVisit);
        unshare.setCorrelationId("1", "0");
        unshare.setRoomTypeCode("1", firstRoomTypeCode);
        unshare.setRsrReservation("1", firstRsrReservation);
        unshare.setTcgId("1", firstBooking.getTravelComponentGroupingId());
        unshare.setTcId("1", firstBooking.getTravelComponentId());
        unshare.setTravelStatus("1", "BOOKED");
        unshare.setShared("1", firstShared);
        unshare.setUnsharedRoomTpsId("1", firstBooking.getTravelPlanSegmentId());

        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[1]/unSharedRoomDetail",
        // BaseSoapCommands.ADD_NODE.commandAppend("locationId"));
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[1]/unSharedRoomDetail/locationId",
        // getLocationId());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/emailDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());

        // second unshare
        unshare.setBookingDate("2", date(0));
        unshare.setOverrideFreeze("2", secondOverrideFreeze);
        unshare.setPackageCode("2", secondPackageCode);
        unshare.setResortCode("2", secondResortCode);
        unshare.setAdditionalChargeOverride("2", secondaddChargeOverride);
        unshare.setRateDetailsBasePrice("2", secondBasePrice);
        unshare.setRateDetailsDate("2", date(0));
        unshare.setRateDetailsOverriden("2", secondDetailsOverridden);
        unshare.setRateDetailsShared("2", secondDetailShared);
        unshare.setResortEndDate("2", getDepartureDate());
        unshare.setResortStartDate("2", getArrivalDate());
        unshare.setGuestAge("2", secondGuestAge);
        unshare.setGuestType("2", "ADULT");
        unshare.setGuestFirstName("2", guest2FirstName);
        unshare.setGuestLastName("2", guest2LastName);
        unshare.setGuestMiddleName("2", "Automation");
        unshare.setGuestPartyId("2", "0");
        unshare.setGuestDoNotMailIndicator("2", secondDontMail);
        unshare.setGuestDoNotPhoneIndicator("2", secondDontPhone);
        unshare.setGuestId("2", secondBooking.getGuestId());
        unshare.setGuestDclGuestId("2", "0");
        unshare.setGuestActive("2", secondActive);
        unshare.setGuestPurposeOfVisit("2", secondGuestPurposeOfVisit);
        unshare.setCorrelationId("2", "0");
        unshare.setRoomTypeCode("2", secondRoomTypeCode);
        unshare.setRsrReservation("2", secondRsrReservation);
        unshare.setTcgId("2", secondBooking.getTravelComponentGroupingId());
        unshare.setTcId("2", secondBooking.getTravelComponentId());
        unshare.setTravelStatus("2", "BOOKED");
        unshare.setShared("2", secondShared);
        unshare.setUnsharedRoomTpsId("2", firstBooking.getTravelPlanSegmentId());

        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[2]/unSharedRoomDetail",
        // BaseSoapCommands.ADD_NODE.commandAppend("locationId"));
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[2]/unSharedRoomDetail/locationId",
        // getLocationId());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[2]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[2]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/emailDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());
        // unshare.setRequestNodeValueByXPath("/Envelope/Body/unshareAccommodations/request/shareChains/shareRoomDetails[2]/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails/guest/phoneDetails",
        // BaseSoapCommands.REMOVE_NODE.toString());

        unshare.sendRequest();

        TestReporter.logAPI(!unshare.getResponseStatusCode().equals("200"), "An error occurred sending the request: " + unshare.getFaultString(), unshare);

    }

    public String date(int daysOut) {
        return Randomness.generateCurrentXMLDatetime(daysOut);
    }

}
