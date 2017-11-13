
package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Checkout_WDTC extends AccommodationBaseTest {
    private CheckInHelper helper;

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
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        bookReservation();
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation" })
    public void TestCheckout_wdtc() {
        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Response states Invalid Accommodation Type, Fix is in progress");
        // }
        // }

        helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        String tcgId = getBook().getTravelComponentGroupingId();
        String checkoutDate = Randomness.generateCurrentXMLDate();
        String refNumber = getExternalRefNumber();
        String refSource = getExternalRefSource();

        Checkout checkout = new Checkout(getEnvironment(), "main");
        checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setTravelComponentGroupingId(tcgId);
        checkout.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setExternalReferenceNumber(refNumber);
        checkout.setExternalReferenceSource(refSource);
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setCheckoutDate(checkoutDate);
        checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.sendRequest();

        String assignOwnerId = validateResMgmt(getBook().getTravelComponentId(), checkout);
        validateRIM(assignOwnerId, checkout);
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
            throw new SQLValidationException("No charges found for assign owner ID [ " + assignOwnerId + " ]", sql);
        } else {
            TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that 1 record was returned.");
            TestReporter.softAssertTrue(rs.getValue("AUTO_ASGN_RSRC_ID").equals("NULL"), "Verify that the auto asign resource ID [" + rs.getValue("AUTO_ASGN_RSRC_ID") + "] is that which is expected [NULL].");
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
            throw new SQLValidationException("No charges found for tc_grp_nb [ " + getBook().getTravelComponentGroupingId() + " ]", sql);
        } else {
            TestReporter.softAssertEquals(rs.getValue("TC_RSN_TYP_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_TYP_NM") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(rs.getValue("LGCY_RSN_CD"), "NULL", "Verify that the TC reason type [" + rs.getValue("LGCY_RSN_CD") + "] is that which is expected [NULL].");
            TestReporter.softAssertEquals(rs.getValue("TC_RSN_NM"), "NULL", "Verify that the TC reason type [" + rs.getValue("TC_RSN_NM") + "] is that which is expected [NULL].");
            TestReporter.assertAll();
        }
    }

    public String validateResMgmt(String TcId, Checkout checkout) {
        String tcId = getBook().getTravelComponentId();

        TestReporter.logStep("Verify Res Mgmt");
        String sql = "select c.* " + " from res_mgmt.tps a "
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb " + " where tc_id = "
                + getBook().getTravelComponentId();
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tcId [ " + getBook().getTravelComponentId() + " ]", sql);
        } else {
            String assignOwnerId = null;
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (rs.getValue("TC_ID", i).equals(TcId)) {
                    assignOwnerId = rs.getValue("ASGN_OWN_ID");

                    TestReporter.softAssertTrue(rs.getValue("TC_ID").equals(tcId), "Verify TcId is set");
                }
            }

            sql = "select a.trvl_sts_nm TPS_STS, TC_CHKOT_DTS, TC_CHKIN_DTS, c.TRVL_STS_NM TC_STS "
                    + "from res_mgmt.tps a "
                    + "left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                    + "left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                    + "where a.tp_id = '" + getBook().getTravelPlanId() + "' "
                    + "and c.tc_typ_nm = 'AccommodationComponent'";

            db = new OracleDatabase(environment, Database.DREAMS);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == 0) {
                throw new SQLValidationException("No charges found for tcId [ " + getBook().getTravelComponentId() + " ]", sql);
            } else {
                TestReporter.softAssertEquals(rs.getValue("TPS_STS"), "Past Visit", "Verify that the TPS status [" + rs.getValue("TPS_STS") + "] is that which is expected [Past Visit].");
                TestReporter.softAssertEquals(rs.getValue("TC_CHKOT_DTS").split(" ")[0], Randomness.generateCurrentXMLDate(), "Verify that the checkout date [" + rs.getValue("TC_CHKOT_DTS").split(" ")[0] + "] is that which is expected [" + Randomness.generateCurrentXMLDate() + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_STS"), "Past Visit", "Verify that the TC status [" + rs.getValue("TC_STS") + "] is that which is expected [Past Visit].");

                TestReporter.assertAll();
                return assignOwnerId;
            }
        }
    }

    public void validateRIM(String assignOwnerId, Checkout checkout) {
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
                + "where a.EXTNL_REF_VAL in ('" + getBook().getTravelPlanId() + "','" + getBook().getTravelPlanSegmentId() + "','" + getBook().getTravelComponentGroupingId() + "') "
                + "and folio_sts_nm is not null";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertTrue(rs.getRowCount() == 2, "Verify that 2 folios were found.");
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
        TestReporter.softAssertTrue(rs.getRowCount() == 17, "Verify that 17 charges were found.");
        do {
            TestReporter.softAssertEquals(rs.getValue("CHRG_PST_ST_NM"), "Earned", "Verify that the charge past state name [" + rs.getValue("CHRG_PST_ST_NM") + "] is that which is expected [Earned].");
            TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Verify that the charge active indicator [" + rs.getValue("CHRG_ACTV_IN") + "] is that which is expected [Y].");
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