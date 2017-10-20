package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.date.DateTimeConversion;

public class Checkout_Cancel extends AccommodationBaseTest {
    private CheckInHelper checkInHelper;
    private String firstTcg;

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
        setSendRequest(false);
        bookReservation();
        getBook().setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second accommodation: " + getBook().getFaultString(), getBook());
        firstTcg = getBook().getTravelComponentGroupingId();

        getBook().setTravelPlanId(getBook().getTravelPlanId());
        getBook().setTravelPlanSegementId(getBook().getTravelPlanSegmentId());
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second accommodation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "debug" })
    public void TestCheckout_roomOnly_multAccomm_cancelOne_checkInOne_checkoutOne() {
        // Cancel One
        TestReporter.logScenario("Cancel");
        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(firstTcg);
        cancel.setExternalReferenceType("RESERVATION");
        cancel.setExternalReferenceNumber(getExternalRefNumber());
        cancel.setExternalReferenceSource(externalRefSource);
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        // Checkin One and then Checkout One
        TestReporter.logScenario("Checkin One");
        checkInHelper = new CheckInHelper(getEnvironment(), getBook());
        checkInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        TestReporter.logScenario("Checkout One");
        checkInHelper.checkOut(getLocationId());

        String assignOwnerId = validateResMgmt(getBook().getTravelComponentId());
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
        if (rs.getRowCount() == 0) {
            throw new SkipException("No approved records found in recordset ");
        } else {
            TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that 1 record was returned.");
            TestReporter.softAssertTrue(!rs.getValue("AUTO_ASGN_RSRC_ID").equals("NULL"), "Verify that the auto asign resource ID [" + rs.getValue("AUTO_ASGN_RSRC_ID") + "] is not null.");
            TestReporter.softAssertTrue(rs.getValue("OWNR_STS_NM").equals("COMPLETED"), "Verify that the owner status [" + rs.getValue("OWNR_STS_NM") + "] is that which is expected [COMPLETED].");
            TestReporter.softAssertTrue(rs.getValue("RSRC_ASGN_REQ_ID").equals("NULL"), "Verify that the resource assingment request ID [" + rs.getValue("RSRC_ASGN_REQ_ID") + "] is that which is expected [NULL].");
            TestReporter.softAssertTrue(rs.getValue("ASGN_ID").equals("NULL"), "Verify that the assignment ID [" + rs.getValue("ASGN_ID") + "] is that which is expected [NULL].");
            TestReporter.assertAll();
        }
    }

    private void validateCheckoutReason() {
        TestReporter.logStep("Verify checkout reason");
        String sql = "select a.tc_id, b.TC_RSN_TYP_NM, c.LGCY_RSN_CD, c.TC_RSN_NM "
                + "from res_mgmt.tc a "
                + "left outer join res_mgmt.tc_rsn b on a.tc_id = b.tc_id "
                + "left outer join res_mgmt.prdf_tc_rsn c on b.PRDF_TC_RSN_ID = c.PRDF_TC_RSN_ID "
                + "where a.tc_grp_nb = '" + getBook().getTravelComponentGroupingId() + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + getBook().getTravelComponentGroupingId() + " ]", sql);
        } else {
            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that TC reason records were generated");
            do {
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_TYP_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_TYP_NM") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("LGCY_RSN_CD"), "NULL", "Verify that the TC reason type [" + rs.getValue("LGCY_RSN_CD") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("TC_RSN_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_NM") + "] is that which is expected [NULL].");
                rs.moveNext();
            } while (rs.hasNext());
            TestReporter.assertAll();
        }
    }

    public String validateResMgmt(String TcId) {
        String tcId = getBook().getTravelComponentId();

        TestReporter.logStep("Verify Res Mgmt");
        String sql = "select c.* " + " from res_mgmt.tps a "
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb " + " where tc_id = "
                + getBook().getTravelComponentId();
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String assignOwnerId = null;
        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + getBook().getTravelComponentGroupingId() + " ]", sql);
        } else {
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (rs.getValue("TC_ID", i).equals(TcId)) {
                    assignOwnerId = rs.getValue("ASGN_OWN_ID");

                    TestReporter.softAssertTrue(rs.getValue("TC_ID").equals(tcId), "Verify TcId is set");
                }
            }
        }
        sql = "select a.trvl_sts_nm TPS_STS, TC_CHKOT_DTS, TC_CHKIN_DTS, c.TRVL_STS_NM TC_STS, c.TC_ID "
                + "from res_mgmt.tps a "
                + "left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tp_id = '" + getBook().getTravelPlanId() + "' "
                + "and c.tc_typ_nm = 'AccommodationComponent'";

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        do {
            if (rs.getValue("TC_ID").equals(TcId)) {

                TestReporter.softAssertEquals(rs.getValue("TPS_STS"), "Past Visit", "Verify that the TPS status [" + rs.getValue("TPS_STS") + "] is that which is expected [Past Visit].");
                TestReporter.softAssertEquals(rs.getValue("TC_CHKOT_DTS").split(" ")[0], Randomness.generateCurrentXMLDate(), "Verify that the checkout date [" + rs.getValue("TC_CHKOT_DTS").split(" ")[0] + "] is that which is expected [" + Randomness.generateCurrentXMLDate() + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_STS"), "Past Visit", "Verify that the TC status [" + rs.getValue("TC_STS") + "] is that which is expected [Past Visit].");
            } else {

                TestReporter.softAssertEquals(rs.getValue("TPS_STS"), "Past Visit", "Verify that the TPS status [" + rs.getValue("TPS_STS") + "] is that which is expected [Past Visit].");
                TestReporter.softAssertEquals(rs.getValue("TC_CHKOT_DTS"), "NULL", "Verify that the checkout date [" + rs.getValue("TC_CHKOT_DTS") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("TC_STS"), "Cancelled", "Verify that the TC status [" + rs.getValue("TC_STS") + "] is that which is expected [Cancelled].");
            }
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.assertAll();
        return assignOwnerId;
    }

    public void validateRIM(String assignOwnerId) {
        TestReporter.logStep("Validate RIM");
        String sql = " Select RSRC_INVTRY_TYP_ID, AUTO_ASGN_RSRC_ID, OWNR_STS_NM, ASGN_OWNR_ID "
                + " From rsrc_inv.RSRC_ASGN_OWNR " + " Where ASGN_OWNR_ID = " + assignOwnerId;
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

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
                + "where a.EXTNL_REF_VAL in ('" + getBook().getTravelPlanId() + "','" + getBook().getTravelPlanSegmentId() + "','" + getBook().getTravelComponentGroupingId() + "') "
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
                + "where a.EXTNL_REF_VAL in ('" + getBook().getTravelPlanId() + "','" + getBook().getTravelPlanSegmentId() + "','" + getBook().getTravelComponentGroupingId() + "') "
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
        String sql = "select CHRG_GRP_STS_NM, CHRG_GRP_ACTV_IN "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ('" + getBook().getTravelPlanId() + "','" + getBook().getTravelPlanSegmentId() + "','" + getBook().getTravelComponentGroupingId() + "')";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 3, "Verify that 3 charge groups were found.");
        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM"), "Past Visit", "Verify that the charge group status [" + rs.getValue("CHRG_GRP_STS_NM") + "] is that which is expected [Past Visit].");
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_ACTV_IN"), "Y", "Verify that the charge group active indicator [" + rs.getValue("CHRG_GRP_STS_NM") + "] is that which is expected [Y].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }
}