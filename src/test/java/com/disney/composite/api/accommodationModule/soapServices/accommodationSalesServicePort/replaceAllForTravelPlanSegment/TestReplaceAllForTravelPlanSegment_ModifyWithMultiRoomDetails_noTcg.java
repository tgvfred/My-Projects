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

public class TestReplaceAllForTravelPlanSegment_ModifyWithMultiRoomDetails_noTcg extends AccommodationBaseTest {
    private String tpPtyId = null;
    String tcg2 = null;
    String tcg3 = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;
    private String extRefNum = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
        extRefNum = getExternalRefNumber();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_ModifyWithMultiRoomDetails_noTcg() {
        setSendRequest(false);
        setAddRoom(true);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setReplaceAll("true");
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying to a group booking: " + getBook().getFaultString(), getBook());
        tpPtyId = getBook().getGuestId();
        tcg2 = getBook().getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails[1]/travelComponentGroupingId");
        tcg3 = getBook().getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails[2]/travelComponentGroupingId");

        validations();

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            ReplaceAllForTravelPlanSegment clone = (ReplaceAllForTravelPlanSegment) getBook().clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getBook(), true), "Validating Response Comparison");

            try {
                Cancel cancel = new Cancel(getEnvironment(), "Main");
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
        tcgIds.put(tcgId, tcgId);
        tcgIds.put(tcg2, tcg2);
        tcgIds.put(tcg3, tcg3);
        validations.validateModificationBackendMultiAccomm(6, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgIds);
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId(), 3);
        validations.verifyTcStatusByTcg(tcgId, "Booked");
        validations.verifyTcStatusByTcg(tcg2, "Booked");
        validations.verifyTcStatusByTcg(tcg3, "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcgId, getHouseHold().primaryGuest());
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcg2, getHouseHold().primaryGuest());
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), tcg3, getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 3, getBook().getTravelPlanId());
        validations.verifyChargeDetail(12, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 5, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(tcgId, 1, getBook().getTravelPlanId());
        validations.verifyInventoryAssigned(tcg2, 1, getBook().getTravelPlanId());
        validations.verifyInventoryAssigned(tcg3, 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true, 3);

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
        partyId = getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings[3]/accommodation/guestReferences/guest/guestId");
        tpPartyIds.put(partyId, partyId);

        validations.verifyOdsGuestIdCreated(false, partyId);
        validations.verifyTpPartyIds(tpPartyIds, getBook().getTravelPlanId());
        getAdditionalGuests().get(1).setPrimary(false);
        validations.verifyTpPartyIds(tpPartyIds, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());
        validations.validateTPV3(getBook().getTravelPlanId(), "Booked", getArrivalDate(), getDepartureDate(), getHouseHold().primaryGuest(), 3, 3, "N", "NULL", getFacilityId(), getAdditionalGuests());
    }
}
