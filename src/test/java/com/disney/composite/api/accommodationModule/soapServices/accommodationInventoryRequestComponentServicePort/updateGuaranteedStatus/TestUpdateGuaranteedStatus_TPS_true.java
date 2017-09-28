package com.disney.composite.api.accommodationModule.soapServices.accommodationInventoryRequestComponentServicePort.updateGuaranteedStatus;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort.operations.UpdateGuaranteedStatus;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestUpdateGuaranteedStatus_TPS_true extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);
        isComo.set("false");
        bookReservation();

    }

    String tp_id = "";
    String tps_id = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationInventoryRequestComponentService", "updateGuaranteedStatus" })
    public void testUpdateGuaranteedStatus_TPS_true() {

        tp_id = getBook().getTravelPlanId();

        String sql1 = " select c.ASGN_OWN_ID"
                + " from res_mgmt.tps a"
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tp_id = '" + tp_id + "'"
                + " and c.ASGN_OWN_ID is not null";

        String sql2 = " select d.GUAR_IN"
                + " from res_mgmt.tps a"
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " left outer join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWN_ID = d.ASGN_OWNR_ID"
                + " where a.tp_id =  '" + tp_id + "'"
                + " and c.ASGN_OWN_ID is not null";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));

        TestReporter.assertNotNull(rs.getValue("ASGN_OWN_ID"), "The assignment owner id is " + rs.getValue("ASGN_OWN_ID") + "].");
        tps_id = getBook().getTravelPlanSegmentId();

        UpdateGuaranteedStatus ugs = new UpdateGuaranteedStatus(Environment.getBaseEnvironmentName(getEnvironment()));
        ugs.setGuaranteedStatusFlag("true");
        ugs.setOwnerReferenceNumber(tps_id);
        ugs.setOwnerReferenceType("TPS");
        ugs.sendRequest();

        Recordset rs2 = new Recordset(db.getResultSet(sql2));

        // validation
        TestReporter.logAPI(!ugs.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", ugs);
        TestReporter.assertTrue(ugs.getAssignmentOwnerId().equals(rs.getValue("ASGN_OWN_ID")), "The response Assignment Owner Id [" + ugs.getAssignmentOwnerId() + "] matches the database TC_RSN_NM [" + rs.getValue("ASGN_OWN_ID") + "].");
        TestReporter.assertTrue("Y".equals(rs2.getValue("GUAR_IN")), "The Guarante Indicator is set to [" + rs2.getValue("GUAR_IN") + "].");

        // old vs. new

        if (Environment.isSpecialEnvironment(getEnvironment())) {

            UpdateGuaranteedStatus clone = (UpdateGuaranteedStatus) ugs.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (ugs.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/updatedGuaranteedStatusResponse/return/");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(ugs, true), "Validating Response Comparison");

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationInventoryRequestComponentService", "updateGuaranteedStatus" }, dependsOnMethods = { "testUpdateGuaranteedStatus_TPS_true" })
    public void testUpdateGuaranteedStatus_TPS_trueToFalse() {

        // tp_id = getBook().getTravelPlanId();

        String sql1 = " select c.ASGN_OWN_ID"
                + " from res_mgmt.tps a"
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tp_id = '" + tp_id + "'"
                + " and c.ASGN_OWN_ID is not null";

        String sql2 = " select d.GUAR_IN"
                + " from res_mgmt.tps a"
                + " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " left outer join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWN_ID = d.ASGN_OWNR_ID"
                + " where a.tp_id =  '" + tp_id + "'"
                + " and c.ASGN_OWN_ID is not null";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));

        TestReporter.assertNotNull(rs.getValue("ASGN_OWN_ID"), "The assignment owner id is " + rs.getValue("ASGN_OWN_ID") + "].");
        // tps_id = getBook().getTravelPlanSegmentId();

        UpdateGuaranteedStatus ugs = new UpdateGuaranteedStatus(Environment.getBaseEnvironmentName(getEnvironment()));
        ugs.setGuaranteedStatusFlag("false");
        ugs.setOwnerReferenceNumber(tps_id);
        ugs.setOwnerReferenceType("TPS");
        ugs.sendRequest();

        Recordset rs2 = new Recordset(db.getResultSet(sql2));
        // validations

        TestReporter.logAPI(!ugs.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", ugs);
        TestReporter.assertTrue(ugs.getAssignmentOwnerId().equals(rs.getValue("ASGN_OWN_ID")), "The response Assignment Owner Id [" + ugs.getAssignmentOwnerId() + "] matches the database TC_RSN_NM [" + rs.getValue("ASGN_OWN_ID") + "].");
        TestReporter.assertTrue("N".equals(rs2.getValue("GUAR_IN")), "The Guarante Indicator is set to [" + rs2.getValue("GUAR_IN") + "].");

        // old vs. new
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            UpdateGuaranteedStatus clone = (UpdateGuaranteedStatus) ugs.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (ugs.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/updatedGuaranteedStatusResponse/return/");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(ugs, true), "Validating Response Comparison");

        }
    }
}
