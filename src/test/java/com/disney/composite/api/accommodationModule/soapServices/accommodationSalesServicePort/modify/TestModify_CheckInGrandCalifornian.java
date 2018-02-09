package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.accommodationSales.operations.Cancel;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestModify_CheckInGrandCalifornian extends BookDVCCashHelper {

    private String tpPtyId = null;
    private String tpId = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setUseDvcResort(true);
        setValues("305669", "5B", "10068", "15");
        setUseExistingValues(true);
        setRetrieveAfterBook(false);
        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        tpId = (getFirstBooking().getTravelPlanId());
        tpPtyId = getFirstBooking().getPartyId();
        DVCSalesBaseTest.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "GCAL" })
    public void testModify_CheckInGrandCalifornian() {
        Modify modify = new Modify(getFirstBooking());
        modify.setTravelStatus("Checked In");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);

        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(getEnvironment()));

        // Validate reservation
        validations.validateModificationBackend(2, "Checked In", "", getArrivalDate(), getDepartureDate(), "NULL", "NULL",
                modify.getTravelPlanId(), modify.getTravelPlanSegmentId(), modify.getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(modify.getTravelPlanId());
        validations.verifyTcStatusByTcg(modify.getTravelComponentGroupingId(), "Checked In");

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
        validations.verifyTpPartyId(modify.getGuestId(), modify.getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, modify.getTravelPlanId());

        validations.validateResortAndRoomType(modify.getTravelPlanId(), getFacilityId(), getRoomTypeCode());
        validations.validateAreaPeriod(modify.getTravelPlanId(), getArrivalDate(), getDepartureDate());
        getHouseHold().primaryGuest().setFirstName(getFirstMember().getMemberFirstName());
        getHouseHold().primaryGuest().setLastName(getFirstMember().getMemberLastName());
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
