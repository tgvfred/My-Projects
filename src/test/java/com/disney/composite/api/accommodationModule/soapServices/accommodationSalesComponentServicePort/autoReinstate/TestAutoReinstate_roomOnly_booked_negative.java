package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_roomOnly_booked_negative extends AccommodationBaseTest {

    AutoReinstate auto;
    Cancel cancel;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(20);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate", "negative" })
    public void Test_AutoReinstate_roomOnly_booked_negative() {

        auto = new AutoReinstate(environment, "Main");
        auto.setFreezeId(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        auto.sendRequest();

        String faultString = "This reservation is not eligible to be Re-instated. Please contact the appropriate Reservation Office, Operations Support or Manager for assistance : Reservation is Not in Cancelled or AutoCancelled Status";

        validateApplicationError(auto, AccommodationErrorCode.REINSTATE_NOT_ALLOWED);
        TestReporter.assertEquals(faultString, auto.getFaultString(), "Verify that the fault string [" + auto.getFaultString() + "] is that which is expected.[" + faultString + "]");
    }

}
