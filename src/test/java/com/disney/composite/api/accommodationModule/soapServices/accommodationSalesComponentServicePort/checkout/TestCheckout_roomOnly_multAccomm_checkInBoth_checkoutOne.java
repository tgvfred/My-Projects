package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCheckout_roomOnly_multAccomm_checkInBoth_checkoutOne extends AccommodationBaseTest {
    private CheckInHelper helper;
    // private AddAccommodationHelper accommHelper;
    // private Add add;
    private String locVar;
    private AccommodationBaseTest base;
    private ReplaceAllForTravelPlanSegment book;

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setEnvironment("latest");
        locVar = environment;
        bookReservation();
        base = this;
        book = base.getBook();

        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(book.getTravelPlanId());
        getBook().setTravelPlanSegementId(book.getTravelPlanSegmentId());
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second accommodation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "debug" })
    public void testCheckout_roomOnly_multAccomm_checkInBoth_checkoutOne() {
        // Checkin the first accommodation
        helper = new CheckInHelper(locVar, getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        // Checkin and checkout the second accommodation
        helper = new CheckInHelper(getEnvironment(), book);
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        helper.checkOut(getLocationId());

        String assignOwnerId = validateResMgmt(book.getTravelComponentId());
        validateRIM(assignOwnerId);
        additionalValidations(assignOwnerId);
        validateChargeGroupsChargesAndFolio();
    }

    private void additionalValidations(String assignOwnerId) {
        validateCheckoutReason();
        validateRIMInventoryReleased(assignOwnerId);
    }

    private void validateRIMInventoryReleased(String assignOwnerId) {
        String sql = "select a.RSRC_INVTRY_TYP_ID, a.AUTO_ASGN_RSRC_ID, a.OWNR_STS_NM, b.RSRC_ASGN_REQ_ID, c.ASGN_ID "
                + "from rsrc_inv.RSRC_ASGN_OWNR a "
                + "left outer join rsrc_inv.RSRC_ASGN_REQ b on a.ASGN_OWNR_ID = b.ASGN_OWNR_ID "
                + "left outer join rsrc_inv.RSRC_ASGN c on b.RSRC_ASGN_REQ_ID = c.RSRC_ASGN_REQ_ID "
                + "where a.ASGN_OWNR_ID = '" + assignOwnerId + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that 1 record was returned.");
        TestReporter.softAssertTrue(!rs.getValue("AUTO_ASGN_RSRC_ID").equals("NULL"), "Verify that the auto asign resource ID [" + rs.getValue("AUTO_ASGN_RSRC_ID") + "] is not null.");
        TestReporter.softAssertTrue(rs.getValue("OWNR_STS_NM").equals("COMPLETED"), "Verify that the owner status [" + rs.getValue("OWNR_STS_NM") + "] is that which is expected [COMPLETED].");
        TestReporter.softAssertTrue(rs.getValue("RSRC_ASGN_REQ_ID").equals("NULL"), "Verify that the resource assingment request ID [" + rs.getValue("RSRC_ASGN_REQ_ID") + "] is that which is expected [NULL].");
        TestReporter.softAssertTrue(rs.getValue("ASGN_ID").equals("NULL"), "Verify that the assignment ID [" + rs.getValue("ASGN_ID") + "] is that which is expected [NULL].");
        TestReporter.assertAll();
    }

    private void validateCheckoutReason() {
        TestReporter.logStep("Verify checkout reason");
        String sql = "select a.tc_id, b.TC_RSN_TYP_NM, c.LGCY_RSN_CD, c.TC_RSN_NM "
                + "from res_mgmt.tc a "
                + "left outer join res_mgmt.tc_rsn b on a.tc_id = b.tc_id "
                + "left outer join res_mgmt.prdf_tc_rsn c on b.PRDF_TC_RSN_ID = c.PRDF_TC_RSN_ID "
                + "where a.tc_grp_nb = '" + book.getTravelComponentGroupingId() + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        do {
            TestReporter.softAssertEquals(rs.getValue("TC_RSN_TYP_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_TYP_NM") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(rs.getValue("LGCY_RSN_CD"), "NULL", "Verify that the TC reason type [" + rs.getValue("LGCY_RSN_CD") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(rs.getValue("TC_RSN_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_NM") + "] is that which is expected [NULL].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public String validateResMgmt(String TcId) {
        String tcId = book.getTravelComponentId();

        TestReporter.logStep("Verify Res Mgmt");
        String sql = "select c.* " + " from res_mgmt.tps a "
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb " + " where tc_id = "
                + book.getTravelComponentId();
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // rs.print();
        String assignOwnerId = null;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("TC_ID", i).equals(TcId)) {
                assignOwnerId = rs.getValue("ASGN_OWN_ID");

                TestReporter.softAssertTrue(rs.getValue("TC_ID").equals(tcId), "Verify TcId is set");
            }
        }

        sql = "select a.trvl_sts_nm TPS_STS, TC_CHKOT_DTS, TC_CHKIN_DTS, c.TRVL_STS_NM TC_STS, c.TC_ID "
                + "from res_mgmt.tps a "
                + "left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tp_id = '" + book.getTravelPlanId() + "' "
                + "and c.tc_typ_nm = 'AccommodationComponent'";

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        do {
            if (rs.getValue("TC_ID").equals(TcId)) {
                TestReporter.softAssertEquals(rs.getValue("TPS_STS"), "Checked In", "Verify that the TPS status [" + rs.getValue("TPS_STS") + "] is that which is expected [Past Visit].");
                TestReporter.softAssertEquals(rs.getValue("TC_CHKOT_DTS").split(" ")[0], Randomness.generateCurrentXMLDate(), "Verify that the checkout date [" + rs.getValue("TC_CHKOT_DTS").split(" ")[0] + "] is that which is expected [" + Randomness.generateCurrentXMLDate() + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_STS"), "Past Visit", "Verify that the TC status [" + rs.getValue("TC_STS") + "] is that which is expected [Past Visit].");
            } else {
                TestReporter.softAssertEquals(rs.getValue("TPS_STS"), "Checked In", "Verify that the TPS status [" + rs.getValue("TPS_STS") + "] is that which is expected [Checked In].");
                TestReporter.softAssertEquals(rs.getValue("TC_CHKOT_DTS"), "NULL", "Verify that the checkout date [" + rs.getValue("TC_CHKOT_DTS") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("TC_STS"), "Checked In", "Verify that the TC status [" + rs.getValue("TC_STS") + "] is that which is expected [Checked In].");
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
        return assignOwnerId;
    }

    public void validateRIM(String assignOwnerId) {
        // String assignOwnerIdValue = assignOwnerId;
        TestReporter.logStep("Validate RIM");
        String sql = " Select RSRC_INVTRY_TYP_ID, AUTO_ASGN_RSRC_ID, OWNR_STS_NM, ASGN_OWNR_ID "
                + " From rsrc_inv.RSRC_ASGN_OWNR " + " Where ASGN_OWNR_ID = " + assignOwnerId;
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that RIM records were found.");
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("ASGN_OWNR_ID", i).equals(assignOwnerId)) {

                TestReporter.assertNotNull(assignOwnerId, "The assigned Owner field was not null");
                TestReporter.assertTrue(rs.getValue("ASGN_OWNR_ID").equals(assignOwnerId), "verify assigned owner is set");
                TestReporter.assertTrue(rs.getValue("OWNR_STS_NM").equals("COMPLETED"), "verify assigned status");
            }
        }
    }

    public void validateChargeGroupsChargesAndFolio() {
        validateChargGroups();
        validateCharges();
        validateFolio();
    }

    public void validateFolio() {
        TestReporter.logStep("Validate folio");
        String sql = "select FOLIO_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID "
                + "left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID "
                + "where a.EXTNL_REF_VAL in ('" + book.getTravelPlanId() + "','" + book.getTravelPlanSegmentId() + "','" + book.getTravelComponentGroupingId() + "') "
                + "and folio_sts_nm is not null";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that 1 folio was found.");
        do {
            TestReporter.softAssertEquals(rs.getValue("FOLIO_STS_NM"), "Earned", "Verify that the foloi status [" + rs.getValue("FOLIO_STS_NM") + "] is that which is expected [Earned].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateCharges() {
        TestReporter.logStep("Validate charges");
        String sql = "select CHRG_ACTV_IN, CHRG_PST_ST_NM, RECOG_STS_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in ('" + book.getTravelPlanId() + "','" + book.getTravelPlanSegmentId() + "','" + book.getTravelComponentGroupingId() + "') "
                + "and CHRG_ACTV_IN is not null";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 4, "Verify that 4 charges were found.");
        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "Earned", "Verify that the charge past state name [" + rs.getValue("CHRG_PST_ST_NM") + "] is that which is expected [Earned].");
            TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "N", "Verify that the charge active indicator [" + rs.getValue("CHRG_ACTV_IN") + "] is that which is expected [N].");
            TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "APPROVED", "Verify that the RECOG status [" + rs.getValue("RECOG_STS_NM") + "] is that which is expected [APPROVED].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateChargGroups() {
        TestReporter.logStep("Validate charge groups");
        String sql = "select a.EXTNL_SRC_NM, CHRG_GRP_STS_NM, CHRG_GRP_ACTV_IN "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + book.getTravelPlanId() + "','" + book.getTravelPlanSegmentId() + "','" + book.getTravelComponentGroupingId() + "')";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 3, "Verify that 3 charge groups were found.");
        do {
            if (rs.getValue("EXTNL_SRC_NM").equals("DREAMS_TCG")) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "Past Visit", "Verify that the charge group status [" + rs.getValue("CHRG_GRP_STS_NM") + "] is that which is expected [Past Visit].");
            } else {
                TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "Earned", "Verify that the charge group status [" + rs.getValue("CHRG_GRP_STS_NM") + "] is that which is expected [Earned].");
            }
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_ACTV_IN"), "Y", "Verify that the charge group active indicator [" + rs.getValue("CHRG_GRP_STS_NM") + "] is that which is expected [Y].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }
}