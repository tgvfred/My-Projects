package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestReplaceAllForTravelPlanSegment_BookWDTCWithMultiRoomDetails extends AccommodationBaseTest {
    String tcg1 = null;
    String tcg2 = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        setAddRoom(true);
        isComo.set("true");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookWithMultiRoomDetails() {
        bookReservation();
        tcg1 = getBook().getTravelComponentGroupingId();
        tcg2 = getBook().getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails[2]/travelComponentGroupingId");

        validations();

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            ReplaceAllForTravelPlanSegment clone = (ReplaceAllForTravelPlanSegment) getBook().clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getResponse(), clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getBook(), true),
                    "Validating Response Comparison");

            try {
                Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())), "Main");
                cancel.setCancelDate(Randomness.generateCurrentXMLDate());
                cancel.setTravelComponentGroupingId(clone.getTravelComponentGroupingId());
                cancel.sendRequest();
            } catch (Exception e) {

            }
        }
    }

    private void validations() {
        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())));

        // Validate reservation
        Map<String, String> tcgIds = new HashMap<>();
        tcgIds.put(tcg1, tcg1);
        tcgIds.put(tcg2, tcg2);
        validations.validateModificationBackendMultiAccomm(14, "Booked", "", getArrivalDate(), getDepartureDate(), "NULL", "NULL",
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgIds);
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId(), 2);
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");
        validations.verifyTcStatusByTcg(tcg2, "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcg2, getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 12, getBook().getTravelPlanId());
        validations.verifyChargeDetail(20, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 4, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.verifyInventoryAssigned(tcg2, 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true, 2);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold(), getAdditionalGuests());
        validations.verifyNumberOfTpPartiesByTpId(2, getBook().getTravelPlanId());
        retrieveReservation();
        Map<String, String> tpPartyIds = new HashMap<>();
        String partyId = getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[1]/accommodation/guestReferences/guest/guestId");
        tpPartyIds.put(partyId, partyId);
        validations.verifyOdsGuestIdCreated(false, partyId);
        partyId = getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[2]/accommodation/guestReferences/guest/guestId");
        tpPartyIds.put(partyId, partyId);
        validations.verifyOdsGuestIdCreated(false, partyId);
        validations.verifyTpPartyIds(tpPartyIds, getBook().getTravelPlanId());
        getAdditionalGuests().get(1).setPrimary(false);
        validations.verifyTpPartyIds(tpPartyIds, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());
        // validations.validateTPV3(getBook().getTravelPlanId(), "Booked", getArrivalDate(), getDepartureDate(), getHouseHold().primaryGuest(), 2, 2, "N", "NULL", getFacilityId(), getAdditionalGuests());
    }
}
