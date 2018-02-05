package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestReplaceAllForTravelPlanSegment_BookMYWPlusDine2Ad2Ch extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String odsGuestId;

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
        setMywPlusDinePackageCode(true);
        isComo.set("true");
        setSendRequest(false);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookMYWPlusDine2Ad2Ch() {
        bookReservation();
        Guest ad2 = new Guest();
        Guest ch1 = new Guest();
        Guest ch2 = new Guest();
        ch1.setAge("5");
        ch2.setAge("9");

        getBook().addRoomDetails_RoomReservationDetail_GuestReferenceDetailGuest(false, false, ad2);
        getBook().addRoomDetails_RoomReservationDetail_GuestReferenceDetailGuest(false, false, ch1);
        getBook().addRoomDetails_RoomReservationDetail_GuestReferenceDetailGuest(false, false, ch2);
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying a shared res: " + getBook().getFaultString(), getBook());
        tpPtyId = getBook().getGuestId();
        String sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a "
                + "join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID "
                + "where a.tp_id = '" + getBook().getTravelPlanId() + "' ";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())), Database.DREAMS);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 60;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            tries++;
            if (rs.getRowCount() > 0) {
                success = true;
            }
        } while ((tries < maxTries) && !success);

        if (rs.getRowCount() == 0) {
            throw new AutomationException("No TXN_PTY_EXTNL_REF_VAL was found in GUEST.TXN_PTY_EXTNL_REF table for TP ID [" + getBook().getTravelPlanId() + "].");
        }
        odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL");

        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())));

        // Validate reservation
        validations.validateModificationBackend(38, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), "01825");
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 29, getBook().getTravelPlanId());
        validations.verifyChargeDetail(33, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, getBook().getTravelPlanId());
        Map<String, String> groupDelegateSmallBalanceWriteoff = new HashMap<String, String>();
        groupDelegateSmallBalanceWriteoff.put(ServiceConstants.FolioExternalReference.DREAMS_TCG, "N");
        groupDelegateSmallBalanceWriteoff.put(ServiceConstants.FolioExternalReference.DREAMS_TPS, "N");
        groupDelegateSmallBalanceWriteoff.put(ServiceConstants.FolioExternalReference.DREAMS_TP, "Y");
        Map<String, String> guaranteeTypes = new HashMap<String, String>();
        guaranteeTypes.put(ServiceConstants.FolioExternalReference.DREAMS_TCG, "GROUP_GUARANTEED");
        guaranteeTypes.put(ServiceConstants.FolioExternalReference.DREAMS_TPS, "DEPOSIT");
        guaranteeTypes.put(ServiceConstants.FolioExternalReference.DREAMS_TP, "NULL");
        validations.verifyFolioGuaranteeStatus(this, groupDelegateSmallBalanceWriteoff, guaranteeTypes, "N");

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "2", "2", true);
        validations.verifyRIMGuaranteeStatus(this, "Y", "Y");

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            ReplaceAllForTravelPlanSegment clone = (ReplaceAllForTravelPlanSegment) getBook().clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));

            tries = 0;
            maxTries = 20;
            success = false;
            do {
                Sleeper.sleep(1000);
                clone.sendRequest();
                tries++;
                if (clone.getResponseStatusCode().equals("200")) {
                    success = true;
                }
            } while ((tries < maxTries) && !success);

            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
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
            clone.addExcludedXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/locationId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/locationId");
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
}
