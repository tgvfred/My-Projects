package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cancel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCancel_Positive extends AccommodationBaseTest {
    private static Database db;

    @Parameters("environment")
    @BeforeClass(alwaysRun = true)
    public static void beforeClass(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_Booked_TravelComponentGroupingIdOnly_RoomOnly() {
        TestReporter.logScenario("Test - Cancel - Travel Component Grouping ID Only and Room Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();

        validateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel" })
    public void testCancel_CheckingIn_TravelComponentGroupingIdOnly() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();

        CheckInHelper helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkingIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        TestReporter.logScenario("Test - Cancel - Checking In Travel Component Grouping ID Only");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();

        validateSoapResponse(cancel);
        validateSpecialEnvironment(cancel);
    }

    /*
     * Utility Functions
     */
    private void validateSoapResponse(Cancel cancel) {
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "The cancel was not successful.", cancel);
        // Add second validation here
    }

    private void validateSpecialEnvironment(Cancel cancel) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            Cancel cancelBaseLine = (Cancel) cancel.clone();
            cancelBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            cancelBaseLine.sendRequest();
            TestReporter.assertTrue(cancel.validateResponseNodeQuantity(cancelBaseLine), "Response Node Validation Result");
        }
    }

    /*
     * String sql = "SELECT d.TPS_EXTNL_REF_TYP_NM, b.TXN_PTY_EXTNL_REF_VAL "
     * +
     * "FROM RES_MGMT.TP_PTY a, GUEST.TXN_PTY_EXTNL_REF b, RES_MGMT.TPS c, RES_MGMT.TPS_EXTNL_REF d "
     * + "WHERE a.TP_ID = '" + getBook().getTravelPlanId() + "' "
     * + "AND a.TXN_PTY_ID = b.TXN_PTY_ID "
     * + "AND a.TP_ID = c.TP_ID "
     * + "AND c.TPS_ID = d.TPS_ID ";
     * Recordset results = new Recordset(db.getResultSet(sql));
     * TestReporter.assertGreaterThanZero(results.getRowCount());
     */
}
