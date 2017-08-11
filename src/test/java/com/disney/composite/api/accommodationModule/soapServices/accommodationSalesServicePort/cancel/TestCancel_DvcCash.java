package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResm;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_DvcCash extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        DVCSalesBaseTest.environment = environment;
        setUseDvcResort(true);
        bookDvcReservation("testCancel_M$", 1);
        setTpId(getFirstBooking().getTravelPlanId());
        makeCCPayment(environment);

        /*
         * Pausing script to ensure events making it downstream to DVC Corp
         * before attempting cancel
         */
        Sleeper.sleep(5000);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(removeCM(environment), getFirstBooking().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
        try {
            thawInventory(removeCM(environment), getInventoryTrackingId());
        } catch (Exception e) {
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_DvcCash() {
        TestReporter.logScenario("Test Cancel dvc cash negative");

        String faultString = "Cant cancel DVC";

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, LiloResm.ACCOMMODATION_NOT_IN_BOOKED_STATUS);
    }
}