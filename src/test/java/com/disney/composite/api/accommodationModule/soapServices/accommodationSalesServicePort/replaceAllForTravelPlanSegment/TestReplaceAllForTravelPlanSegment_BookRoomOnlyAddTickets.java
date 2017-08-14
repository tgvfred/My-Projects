package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestReplaceAllForTravelPlanSegment_BookRoomOnlyAddTickets extends AccommodationBaseTest {
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
        setAddTickets(true);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookRoomOnly() {
        bookReservation();
        tpPtyId = getBook().getGuestId();
        String sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a "
                + "join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID "
                + "where a.tp_id = '" + getBook().getTravelPlanId() + "' ";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL");

        validations();
    }

    private void validations() {

        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        validations.validateModificationBackend(3, "Booked", "", getArrivalDate(), getDepartureDate(), "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");
        String admissionComponentId = validations.validateAdmissionComponentAdded(getBook().getTravelComponentGroupingId());
        String ticketComponentPrice = validations.validateAdmissionComponentDetails(admissionComponentId, getTicketsHelper().getCode());
        validations.validateAdmissionComponentPrice(getBook().getTravelPlanId(), ticketComponentPrice);

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 2, getBook().getTravelPlanId());
        validations.verifyChargeDetail(5, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());
        validations.verifyGoMasterInfoForNewGuest(getHouseHold().primaryGuest(), odsGuestId);
    }
}
