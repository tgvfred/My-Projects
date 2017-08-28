package com.disney.composite.api.accommodationModule.soapServices._examples;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class BookInClassAndCheckIn extends AccommodationBaseTest {
    private String tpPtyId;
    private String odsGuestId;
    private String assignmentOwnerId;
    private CheckInHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        helper.checkOut(getLocationId());
    }

    @Test(groups = { "api", "regression", "accommodation", "example" })
    public void bookInClassAndCheckIn() {
        helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        gatherDataForValidations();
        validations();

    }

    private void gatherDataForValidations() {
        String sql = "select a.TXN_PTY_ID, b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                + "where a.tp_id = '" + getBook().getTravelPlanId() + "' "
                + "and a.TXN_PTY_ID = b.TXN_PTY_ID";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        tpPtyId = rs.getValue("TXN_PTY_ID", 1);
        odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL", 1);
        assignmentOwnerId = getAssignmentOwnerId(getBook().getTravelPlanId());
    }

    private void validations() {
        ValidationHelper helper = new ValidationHelper(getEnvironment());
        helper.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        helper.verifyChargeGroupsStatusCount("Earned", 3, getBook().getTravelPlanId());
        helper.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        int charges = getNights() * 4;
        helper.verifyChargeDetail(charges, getBook().getTravelPlanId());
        helper.verifyNumberOfChargesByStatus("Earned", getNights(), getBook().getTravelPlanId());
        helper.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        helper.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Checked In");
        helper.verifyOdsGuestIdCreated(getBook().getTravelPlanId(), true);
        helper.validateModificationBackend(1, "Checked In", "DVC", getArrivalDate(), getDepartureDate(), "", "", getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), false);
        helper.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());

        helper.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        helper.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        helper.verifyOdsGuestIdChanged(odsGuestId, false, getBook().getTravelPlanId());
        helper.verifyGoMasterInfoForNewGuest(getHouseHold().primaryGuest(), odsGuestId);
        helper.verifyAssignmentOwnerIdChanged(assignmentOwnerId, false, getBook().getTravelPlanId());
        helper.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);
        helper.verifyInventoryTrackingIdInRIM(getBook().getTravelPlanId(), "", false);

        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);

        TestReporter.logStep("Validate reservation details: TPS");
        String sql = "select a.* from res_mgmt.tps a left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb left outer join res_mgmt.tc_gst d on c.tc_id = d.tc_id left outer join res_mgmt.tp_pty e on a.tp_id = e.tp_id where a.tp_id = '" + getBook().getTravelPlanId() + "' ";
        Recordset tpsRs = new Recordset(db.getResultSet(sql));
        // tpsRs.print();
        int i = 0;
        do {
            i++;
            TestReporter.log("Validating record [" + i + "].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_ID"), getBook().getTravelPlanSegmentId(), "Verify that the TPS ID [" + tpsRs.getValue("TPS_ID") + "] is that which is expected [" + getBook().getTravelPlanSegmentId() + "].");
            TestReporter.softAssertEquals(tpsRs.getValue("TP_ID"), getBook().getTravelPlanId(), "Verify that the TP ID [" + tpsRs.getValue("TP_ID") + "] is that which is expected [" + getBook().getTravelPlanId() + "].");
            TestReporter.softAssertEquals(tpsRs.getValue("TRVL_STS_NM"), "Checked In", "Verify that the travel status [" + tpsRs.getValue("TRVL_STS_NM") + "] is that which is expected [Checked In].");
            TestReporter.softAssertEquals(tpsRs.getValue("VIP_LVL_NM"), "0", "Verify that the VIP level [" + tpsRs.getValue("VIP_LVL_NM") + "] is that which is expected [0].");
            TestReporter.softAssertEquals(tpsRs.getValue("TRVL_AGCY_PTY_ID"), "NULL", "Verify that the travel agency party ID [" + tpsRs.getValue("TRVL_AGCY_PTY_ID") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("TRVL_AGT_PTY_ID"), "NULL", "Verify that the travel agent party ID [" + tpsRs.getValue("TRVL_AGT_PTY_ID") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("PRMY_PTY_ID"), "NULL", "Verify that the primary party ID [" + tpsRs.getValue("PRMY_PTY_ID") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_SECUR_VL"), "NULL", "Verify that the TPS security value [" + tpsRs.getValue("TPS_SECUR_VL") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_CNCL_DTS"), "NULL", "Verify that the cancel DTS [" + tpsRs.getValue("TPS_CNCL_DTS") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_CNCL_NB"), "NULL", "Verify that the cancel number [" + tpsRs.getValue("TPS_CNCL_NB") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_GUAR_IN"), "N", "Verify that the TPS guaranteed indicator [" + tpsRs.getValue("TPS_GUAR_IN") + "] is that which is expected [N].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_ARVL_DT").split(" ")[0], getArrivalDate().split("T")[0], "Verify that the TPS arrival date [" + tpsRs.getValue("TPS_ARVL_DT").split(" ")[0] + "] is that which is expected [" + getArrivalDate().split("T")[0] + "].");
            TestReporter.softAssertEquals(tpsRs.getValue("TPS_DPRT_DT").split(" ")[0], getDepartureDate().split("T")[0], "Verify that the TPS departure date [" + tpsRs.getValue("TPS_DPRT_DT").split(" ")[0] + "] is that which is expected [" + getDepartureDate().split("T")[0] + "].");
            TestReporter.softAssertEquals(tpsRs.getValue("ONST_MSG_IN"), "Y", "Verify that the onsite messaging indicator [" + tpsRs.getValue("ONST_MSG_IN") + "] is that which is expected [Y].");
            tpsRs.moveNext();
        } while (tpsRs.hasNext());

        TestReporter.logStep("Validate reservation details: TCG");
        sql = sql.replace("a.*", "b.*");
        Recordset tcgRs = new Recordset(db.getResultSet(sql));
        // tcgRs.print();i = 0;
        do {
            i++;
            TestReporter.log("Validating record [" + i + "].");
            TestReporter.softAssertEquals(tcgRs.getValue("TC_GRP_NB"), getBook().getTravelComponentGroupingId(), "Verify that the TCG ID [" + tcgRs.getValue("TC_GRP_NB") + "] is that which is expected [" + getBook().getTravelComponentGroupingId() + "].");
            TestReporter.softAssertEquals(tcgRs.getValue("ADD_ON_TC_GRP_NB"), "NULL", "Verify that the add-on TCG ID [" + tcgRs.getValue("ADD_ON_TC_GRP_NB") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tcgRs.getValue("TPS_ID"), getBook().getTravelPlanSegmentId(), "Verify that the TPS ID [" + tcgRs.getValue("TPS_ID") + "] is that which is expected [" + getBook().getTravelPlanSegmentId() + "].");
            TestReporter.softAssertEquals(tcgRs.getValue("TC_GRP_TYP_NM"), "ACCOMMODATION", "Verify that the TCG type name [" + tcgRs.getValue("TC_GRP_TYP_NM") + "] is that which is expected [ACCOMMODATION].");
            TestReporter.softAssertEquals(tcgRs.getValue("ADV_INTERNT_CHKIN_IN"), "N", "Verify that the advanced internet checking indicator [" + tcgRs.getValue("ADV_INTERNT_CHKIN_IN") + "] is that which is expected [N].");
            tcgRs.moveNext();
        } while (tcgRs.hasNext());

        TestReporter.logStep("Validate reservation details: TC");
        sql = sql.replace("b.*", "c.*");
        Recordset tcRs = new Recordset(db.getResultSet(sql));
        // tcRs.print();i = 0;
        do {
            i++;
            TestReporter.log("Validating record [" + i + "].");
            if (tcRs.getValue("PROD_TYP_NM").equals("AccommodationProduct")) {
                TestReporter.softAssertEquals(tcRs.getValue("TC_ID"), getBook().getTravelComponentId(), "Verify that the TC ID [" + tcRs.getValue("TC_ID") + "] is that which is expected [" + getBook().getTravelComponentId() + "].");
                TestReporter.softAssertEquals(tcRs.getValue("TC_TYP_NM"), "AccommodationComponent", "Verify that the TC type name [" + tcRs.getValue("TC_TYP_NM") + "] is that which is expected [AccommodationComponent].");
                TestReporter.softAssertEquals(tcRs.getValue("FAC_ID"), getFacilityId(), "Verify that the facility ID [" + tcRs.getValue("FAC_ID") + "] is that which is expected [" + getFacilityId() + "].");
                TestReporter.softAssertEquals(tcRs.getValue("PROD_TYP_NM"), "AccommodationProduct", "Verify that the product type name [" + tcRs.getValue("PROD_TYP_NM") + "] is that which is expected [AccommodationProduct].");
                TestReporter.softAssertEquals(tcRs.getValue("TC_CHRG_IN"), "Y", "Verify that the TC charge indicator [" + tcRs.getValue("TC_CHRG_IN") + "] is that which is expected [Y].");
                TestReporter.softAssertTrue(Regex.match("[0-9]{9}", tcRs.getValue("ASGN_OWN_ID")), "Verify that the assignment owner ID [" + tcRs.getValue("ASGN_OWN_ID") + "] is a 9-digit number as expected.");
                TestReporter.softAssertEquals(tcRs.getValue("TC_INVTRY_IN"), "Y", "Verify that the TC inventory indicator [" + tcRs.getValue("TC_INVTRY_IN") + "] is that which is expected [Y].");
            } else {
                TestReporter.softAssertTrue(Regex.match("[0-9]{10}", tcRs.getValue("TC_ID")), "Verify that the non-accommodation TC ID [" + tcRs.getValue("TC_ID") + "] is a 10-digit number as expected.");
                TestReporter.softAssertEquals(tcRs.getValue("TC_TYP_NM"), "PackageTravelComponent", "Verify that the TC type name [" + tcRs.getValue("TC_TYP_NM") + "] is that which is expected [PackageTravelComponent].");
                TestReporter.softAssertEquals(tcRs.getValue("FAC_ID"), "NULL", "Verify that the facility ID [" + tcRs.getValue("FAC_ID") + "] is that which is expected [NULL].");
                TestReporter.softAssertTrue(tcRs.getValue("PROD_TYP_NM").contains("RO WDW RM"), "Verify that the product type name [" + tcRs.getValue("PROD_TYP_NM") + "] contains that which is expected [RO WDW RM].");
                TestReporter.softAssertEquals(tcRs.getValue("TC_CHRG_IN"), "N", "Verify that the TC charge indicator [" + tcRs.getValue("TC_CHRG_IN") + "] is that which is expected [N].");
                TestReporter.softAssertEquals(tcRs.getValue("ASGN_OWN_ID"), "NULL", "Verify that the assignment owner ID [" + tcRs.getValue("ASGN_OWN_ID") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(tcRs.getValue("TC_INVTRY_IN"), "N", "Verify that the TC inventory indicator [" + tcRs.getValue("TC_INVTRY_IN") + "] is that which is expected [N].");
            }

            TestReporter.softAssertEquals(tcRs.getValue("TC_GRP_NB"), getBook().getTravelComponentGroupingId(), "Verify that the TCG ID [" + tcRs.getValue("TC_GRP_NB") + "] is that which is expected [" + getBook().getTravelComponentGroupingId() + "].");
            TestReporter.softAssertEquals(tcRs.getValue("TC_CHKOT_DTS"), "NULL", "Verify that the TC checkout date [" + tcRs.getValue("TC_CHKOT_DTS") + "] is that which is expected [" + getBook().getTravelComponentGroupingId() + "].");
            TestReporter.softAssertEquals(tcRs.getValue("TC_STRT_DTS").split(" ")[0], getArrivalDate().split("T")[0], "Verify that the TC start date [" + tcRs.getValue("TC_STRT_DTS").split(" ")[0] + "] is that which is expected [" + getArrivalDate().split("T")[0] + "].");
            TestReporter.softAssertEquals(tcRs.getValue("TC_END_DTS").split(" ")[0], getDepartureDate().split("T")[0], "Verify that the TC end date [" + tcRs.getValue("TC_END_DTS").split(" ")[0] + "] is that which is expected [" + getDepartureDate().split("T")[0] + "].");
            TestReporter.softAssertEquals(tcRs.getValue("TC_BK_DTS").split(" ")[0], Randomness.generateCurrentXMLDate(), "Verify that the booking date [" + tcRs.getValue("TC_BK_DTS").split(" ")[0] + "] is that which is expected [" + Randomness.generateCurrentXMLDate() + "].");
            // TestReporter.softAssertEquals(tcRs.getValue("TC_CHKIN_DTS").split(" ")[0], Randomness.generateCurrentXMLDate().split(" ")[0], "Verify that the TC
            // checkin date [" + tcRs.getValue("TC_CHKIN_DTS").split(" ")[0] + "] is that which is expected [" + Randomness.generateCurrentXMLDate().split("
            // ")[0] + "].");
            TestReporter.softAssertEquals(tcRs.getValue("BLK_CD"), "NULL", "Verify that the block code [" + tcRs.getValue("BLK_CD") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tcRs.getValue("TRVL_AGCY_PTY_ID"), "NULL", "Verify that the TC travel agency party ID [" + tcRs.getValue("TRVL_AGCY_PTY_ID") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(tcRs.getValue("TRVL_STS_NM"), "Checked In", "Verify that the TC status [" + tcRs.getValue("TRVL_STS_NM") + "] is that which is expected [Checked In].");
            TestReporter.softAssertEquals(tcRs.getValue("TC_CNCL_DTS"), "NULL", "Verify that the TC cancel date [" + tcRs.getValue("TC_CNCL_DTS") + "] is that which is expected [NULL].");
            tcRs.moveNext();
        } while (tcRs.hasNext());

        TestReporter.logStep("Validate reservation details: TC_GST");
        sql = sql.replace("c.*", "d.*");
        Recordset tcGstRs = new Recordset(db.getResultSet(sql));
        // tcGstRs.print();i = 0;
        do {
            i++;
            TestReporter.log("Validating record [" + i + "].");
            if (!tcGstRs.getValue("TC_ID").equals("NULL")) {
                TestReporter.softAssertTrue(Regex.match("[0-9]{10}", tcGstRs.getValue("TC_GST_ID")), "Verify that the TC Guest ID [" + tcGstRs.getValue("TC_GST_ID") + "] is a 10-digit number as expected.");
                TestReporter.softAssertEquals(tcGstRs.getValue("TC_ID"), getBook().getTravelComponentId(), "Verify that the TC ID [" + tcGstRs.getValue("TC_ID") + "] is that which is expected [" + getBook().getTravelComponentId() + "].");
                TestReporter.softAssertTrue(Regex.match("[0-9]{9}", tcGstRs.getValue("TXN_IDVL_PTY_ID")), "Verify that the transaction individual party ID [" + tcGstRs.getValue("TXN_IDVL_PTY_ID") + "] is a 9-digit number as expected.");
                TestReporter.softAssertEquals(tcGstRs.getValue("AGE_TYP_NM"), "ADULT", "Verify that the age type [" + tcGstRs.getValue("AGE_TYP_NM") + "] is that which is expected [ADULT].");
                TestReporter.softAssertEquals(tcGstRs.getValue("AGE_NB"), getHouseHold().primaryGuest().getAge(), "Verify that the age [" + tcGstRs.getValue("AGE_NB") + "] is that which is expected [" + getHouseHold().primaryGuest().getAge() + "].");
            } else {
                TestReporter.softAssertEquals(tcGstRs.getValue("TC_GST_ID"), "NULL", "Verify that the TC Guest ID [" + tcGstRs.getValue("TC_GST_ID") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(tcGstRs.getValue("TC_ID"), "NULL", "Verify that the TC ID [" + tcGstRs.getValue("TC_ID") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(tcGstRs.getValue("TXN_IDVL_PTY_ID"), "NULL", "Verify that the transaction individual party ID [" + tcGstRs.getValue("TXN_IDVL_PTY_ID") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(tcGstRs.getValue("AGE_TYP_NM"), "NULL", "Verify that the age type [" + tcGstRs.getValue("AGE_TYP_NM") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(tcGstRs.getValue("AGE_NB"), "NULL", "Verify that the age [" + tcGstRs.getValue("AGE_NB") + "] is that which is that which is expected [NULL].");
            }
            tcGstRs.moveNext();
        } while (tcGstRs.hasNext());

        TestReporter.logStep("Validate reservation details: TP_PTY");
        sql = sql.replace("d.*", "e.*");
        Recordset tcPtyRs = new Recordset(db.getResultSet(sql));
        tcPtyRs.print();
        i = 0;
        do {
            i++;
            TestReporter.log("Validating record [" + i + "].");
            TestReporter.softAssertEquals(tcPtyRs.getValue("TP_ID"), getBook().getTravelPlanId(), "Verify that the TP ID [" + tcPtyRs.getValue("TP_ID") + "] is that which is expected [" + getBook().getTravelPlanId() + "].");
            TestReporter.softAssertTrue(Regex.match("[0-9]{9}", tcPtyRs.getValue("TXN_PTY_ID")), "Verify that the transaction party ID [" + tcPtyRs.getValue("TXN_PTY_ID") + "] is is a 9-digit number as expected.");
            TestReporter.softAssertEquals(tcPtyRs.getValue("IDVL_PTY_IN"), "Y", "Verify that the individual party indicator [" + tcPtyRs.getValue("IDVL_PTY_IN") + "] is that which is expected [Y].");
            TestReporter.softAssertEquals(tcPtyRs.getValue("PRMY_PTY_IN"), "Y", "Verify that the primary party indicator [" + tcGstRs.getValue("PRMY_PTY_IN") + "] is that which is expected [Y].");
            tcPtyRs.moveNext();
        } while (tcPtyRs.hasNext());
        TestReporter.assertAll();
    }
}
