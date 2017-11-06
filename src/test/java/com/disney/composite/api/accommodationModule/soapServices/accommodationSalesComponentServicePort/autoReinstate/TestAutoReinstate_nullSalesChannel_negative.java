package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_nullSalesChannel_negative extends AccommodationBaseTest {

    AutoReinstate auto;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate", "negative" })
    public void Test_AutoReinstate_nullSalesChannel_negative() {

        auto = new AutoReinstate(environment, "Main");
        auto.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setFreezeId(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        auto.sendRequest();

        String faultString = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(auto, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
        TestReporter.assertEquals(faultString, auto.getFaultString(), "Verify that the fault string [" + auto.getFaultString() + "] is that which is expected.[" + faultString + "]");
    }

}
