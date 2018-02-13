package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestModify_ModifyRoomOnlyToWDTC extends AccommodationBaseTest {

    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues();
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
        tpPtyId = getBook().getGuestId();
        setSendRequest(false);
        setIsWdtcBooking(true);
        setMywPackageCode(true);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setTravelComponentId(tcId);
        setTpId(tpId);
        setTpsId(tpsId);
        setTcgId(tcgId);
        setTcId(tcId);
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences", BaseSoapCommands.REMOVE_NODE.toString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify" })
    public void testModify_ModifyRoomOnlyToWDTC() {
        Modify modify = new Modify(getBook());
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);
        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(getEnvironment()));

        // Validate reservation
        validations.validateModificationBackend(2, "Booked", "", getArrivalDate(), getDepartureDate(), "NULL", "NULL",
                modify.getTravelPlanId(), modify.getTravelPlanSegmentId(), modify.getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(modify.getTravelPlanId());
        validations.verifyModificationIsFoundInResHistory(modify.getTravelPlanId());
        validations.verifyTcStatusByTcg(modify.getTravelComponentGroupingId(), "Booked");
        validations.verifyPackageTravelComponent(tcgId, "N", "Y", "N");

        // Validate Folio
        validations.verifyNameOnCharges(modify.getTravelPlanId(), modify.getTravelPlanSegmentId(), modify.getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 2, modify.getTravelPlanId());
        validations.verifyChargeDetail(8, modify.getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, modify.getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(modify.getTravelComponentGroupingId(), 1, modify.getTravelPlanId());
        validations.validateSpecialNeeds(modify.getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(modify.getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.verifyNumberOfTpPartiesByTpId(1, modify.getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, modify.getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, modify.getTravelPlanId());

        validations.validateResortAndRoomType(modify.getTravelPlanId(), getFacilityId(), getRoomTypeCode());
        validations.validateAreaPeriod(modify.getTravelPlanId(), getArrivalDate(), getDepartureDate());

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            Modify clone = (Modify) modify.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentGroupingId");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/travelComponentId");
            clone.addExcludedXpathValidations("/Envelope/Body/modifyResponse/modifyResponse/roomDetails/ticketDetails/guestReference/guest/partyId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(modify, true),
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
}
