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

public class TestReplaceAllForTravelPlanSegment_BookRoomOnlyWithMultipleGuests extends AccommodationBaseTest {

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
        setAddNewGuest(true);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookRoomOnlyWithMultipleGuests() {
        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        validations();
    }

    private void validations() {
        int numGuests = getBook().getNumberOfResponseNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails");
        TestReporter.assertTrue(numGuests == 2, "Verify that two guests were returned in the response.");
        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        validations.validateModificationBackend(3, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 1, getBook().getTravelPlanId());
        validations.verifyChargeDetail(4, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "2", "0", true);
        // System.out.println();

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold(), getAdditionalGuests());
        validations.verifyNumberOfTpPartiesByTpId(2, getBook().getTravelPlanId());
        retrieveReservation();
        Map<String, String> tpPartyIds = new HashMap<>();
        String partyId = getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests[1]/guest/guestId");
        tpPartyIds.put(partyId, partyId);
        validations.verifyOdsGuestIdCreated(true, partyId);
        partyId = getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests[2]/guest/guestId");
        tpPartyIds.put(partyId, partyId);
        validations.verifyOdsGuestIdCreated(false, partyId);
        validations.verifyTpPartyIds(tpPartyIds, getBook().getTravelPlanId());

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
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getBook(), true),
                    "Validating Response Comparison");

            try {
                Cancel cancel = new Cancel(getEnvironment(), "Main");
                cancel.setCancelDate(Randomness.generateCurrentXMLDate());
                cancel.setTravelComponentGroupingId(clone.getTravelComponentGroupingId());
                cancel.sendRequest();
            } catch (Exception e) {

            }
        }
    }
}
