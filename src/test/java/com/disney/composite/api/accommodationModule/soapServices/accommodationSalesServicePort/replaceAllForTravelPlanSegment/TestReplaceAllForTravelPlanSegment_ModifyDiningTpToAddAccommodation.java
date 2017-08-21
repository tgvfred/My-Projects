package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
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
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_DVCQueries;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestReplaceAllForTravelPlanSegment_ModifyDiningTpToAddAccommodation extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tcgId = null;
    private ScheduledEventReservation dining;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setHouseHold(new HouseHold(1));
        dining = new ShowDiningReservation(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
        tpId = dining.getTravelPlanId();
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_DVCQueries.getAllTCGsByTpId(tpId)));
        tcgId = rs.getValue("TC_GRP_NB");
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            dining.cancel();
        } catch (Exception e) {

        }

        try {
            cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_ModifyDiningTpToAddAccommodation() {
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        // getBook().setTravelPlanSegementId(tpsId);
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred modifying to a group booking: " + getBook().getFaultString(), getBook());
        tpPtyId = getBook().getGuestId();

        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        Map<String, String> tpsIds = new HashMap<String, String>();
        tpsIds.put("accommodation", getBook().getTravelPlanSegmentId());
        tpsIds.put("dining", dining.getConfirmationNumber());
        Map<String, String> tcgIds = new HashMap<String, String>();
        tcgIds.put("accommodation", getBook().getTravelComponentGroupingId());
        tcgIds.put("dining", tcgId);
        Map<String, String> arrivalDates = new HashMap<String, String>();
        arrivalDates.put("accommodation", getArrivalDate());
        arrivalDates.put("dining", dining.getServiceStartDate().split("T")[0]);
        Map<String, String> departureDates = new HashMap<String, String>();
        departureDates.put("accommodation", getDepartureDate());
        departureDates.put("dining", dining.getServiceStartDate().split("T")[0]);
        validations.validateModificationBackend(5, "Booked", "", arrivalDates, departureDates, "RESERVATION", getExternalRefNumber(),
                getBook().getTravelPlanId(), tpsIds, tcgIds);
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 2, getBook().getTravelPlanId());
        validations.verifyChargeDetail(6, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 6, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());

        Map<String, String> partyIds = new HashMap<>();
        String sql = "select d.TXN_IDVL_PTY_ID "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.tc_gst d on c.tc_id = d.tc_id "
                + "where a.tp_id =  " + getBook().getTravelPlanId();
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        do {
            partyIds.put(rs.getValue("TXN_IDVL_PTY_ID"), "TXN_IDVL_PTY_ID");
            rs.moveNext();
        } while (rs.hasNext());
        validations.validateTPV3(getBook().getTravelPlanId(), "Booked", arrivalDates, departureDates, partyIds, getHouseHold().primaryGuest(), 1, 1, 1, "N", "NULL", getFacilityId());

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
