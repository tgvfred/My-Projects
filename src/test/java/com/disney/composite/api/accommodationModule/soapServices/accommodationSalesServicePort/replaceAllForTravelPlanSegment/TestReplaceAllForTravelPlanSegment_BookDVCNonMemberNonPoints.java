package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

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
import com.disney.utils.dataFactory.database.DVCCorpDatabase;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;

public class TestReplaceAllForTravelPlanSegment_BookDVCNonMemberNonPoints extends AccommodationBaseTest {

    private String tpPtyId = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        // setValues("1", "4S", "83", "14");
        setValues("1", "4S", "83");
        // setValues(getFacilityId(), getRoomTypeCode(), getLocationId());
        // setUseExistingValues(true);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService",
            "replaceAllForTravelPlanSegment", "debug" })
    public void testReplaceAllForTravelPlanSegment_BookDVCNonMemberNonPoints() {
        // System.out.print(getBook().getRequest());
        /// bookDvcReservation("", 1);
        bookReservation();
        getHouseHold().sendToApi(Environment.getBaseEnvironmentName(getEnvironment()));
        tpPtyId = getBook().getGuestId();

        ValidationHelper validations = new ValidationHelper(Environment.getBaseEnvironmentName(getEnvironment()));

        // Validate reservation
        validations.validateModificationBackend(2, "Booked", "", getArrivalDate(), getDepartureDate(), "NULL", "NULL",
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(),
                getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(),
                getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 1, getBook().getTravelPlanId());
        validations.verifyChargeDetail(4, getBook().getTravelPlanId());
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

        // given a non dvc member non points validation in dvc corpDatabase
        String sql = "SELECT * FROM dvcwishes.WPMRESDTL1 WHERE TPID = '" + getBook().getTravelPlanId() + "'";

        Database corpDb = new Database(DVCCorpDatabase.getInfo(environment));

        Recordset rs = new Recordset(corpDb.tryGetResultSetUntil(sql, 8, 10));

        if (rs.getRowCount() != 0) {

            TestReporter.softAssertTrue(rs.getRowCount() >= 1, "A record was returned in the dvc corp database");
            TestReporter.softAssertTrue(getBook().getTravelPlanId().equals(rs.getValue("TPID")),
                    "The travel plan id in the booking [" + getBook().getTravelPlanId()
                            + "] matches the travelPlan in the dvc corp db [" + rs.getValue("TPID") + "]");
            TestReporter.softAssertTrue(getBook().getTravelPlanSegmentId().equals(rs.getValue("TPSID")),
                    "The travel plan segment id in the booking [" + getBook().getTravelPlanSegmentId()
                            + "] matches the travelPlanSegment in the dvc corp db [" + rs.getValue("TPSID") + "]");
            TestReporter.softAssertTrue(getBook().getTravelComponentGroupingId().equals(rs.getValue("TCGID")),
                    "The travel component grouping id in the booking [" + getBook().getTravelComponentGroupingId()
                            + "] matches the travelComponentGrouping in the dvc corp db [" + rs.getValue("TCGID")
                            + "]");
            TestReporter.softAssertTrue(!rs.getValue("GUID").equals(""),
                    "The GUID [" + rs.getValue("GUID") + "] is not null");
            TestReporter.assertAll();
        } else {
            TestReporter.softAssertTrue(rs.getRowCount() >= 1, "A record was NOT returned in the dvc corp database");

        }

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
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getBook(), true),
                    "Validating Response Comparison");

            try {
                Cancel cancel = new Cancel(
                        Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())),
                        "Main");
                cancel.setCancelDate(Randomness.generateCurrentXMLDate());
                cancel.setTravelComponentGroupingId(clone.getTravelComponentGroupingId());
                cancel.sendRequest();
            } catch (Exception e) {

            }
        }
    }
}