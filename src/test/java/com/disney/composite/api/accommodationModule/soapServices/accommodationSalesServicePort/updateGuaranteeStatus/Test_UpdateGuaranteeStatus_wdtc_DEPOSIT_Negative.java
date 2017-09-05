package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_wdtc_DEPOSIT_Negative extends AccommodationBaseTest {

    private String environment;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        isComo.set("false");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_wdtc_DEPOSIT_Negative() {

        String faultString = " Guarantee status can not be changed  : Guarantee status can not be changed";

        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        bookReservation();

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("DEPOSIT");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.GAURANTEE_STATUS_CAN_NOT_BE_CHANGED);
    }

}
