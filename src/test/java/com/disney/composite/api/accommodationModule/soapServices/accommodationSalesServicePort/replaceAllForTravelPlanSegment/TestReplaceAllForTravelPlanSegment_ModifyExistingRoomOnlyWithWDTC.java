package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestReplaceAllForTravelPlanSegment_ModifyExistingRoomOnlyWithWDTC extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String odsGuestId;
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
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
        extRefNum = getExternalRefNumber();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "debug" })
    public void testReplaceAllForTravelPlanSegment_ModifyExistingRoomOnlyWithWDTC() {
        setIsWdtcBooking(true);
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().setTravelComponentGroupingId(tcgId);
        getBook().setTravelComponentId(tcId);
        getBook().setReplaceAll("true");
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying to a group booking: " + getBook().getFaultString(), getBook());

        tpPtyId = getBook().getGuestId();
        String sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a "
                + "join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID "
                + "where a.tp_id = '" + getBook().getTravelPlanId() + "' ";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL");

        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        validations.validateModificationBackend(13, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", extRefNum,
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), "01825");
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyModificationIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 9, getBook().getTravelPlanId());
        validations.verifyChargeDetail(13, getBook().getTravelPlanId());
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
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);
        validations.verifyRIMGuaranteeStatus(this, "Y", "Y");

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());
        validations.verifyGoMasterInfoForNewGuest(getHouseHold().primaryGuest(), odsGuestId);

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
                Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())), "Main");
                cancel.setCancelDate(Randomness.generateCurrentXMLDate());
                cancel.setTravelComponentGroupingId(clone.getTravelComponentGroupingId());
                cancel.sendRequest();
            } catch (Exception e) {

            }
        }
    }
}
