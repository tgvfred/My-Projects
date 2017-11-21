package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_oneSharedRoomDetails_InvalidPartyMix extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        setDaysOut(0);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules" })
    public void testValidateShareRules_oneSharedRoomDetails_InvalidPartyMix() {

        String faultString = "Invalid Party Mix.  Cannot Create Share With Accommodation : Invalid Party Mix. Cannot Create Share With Accommodation";

        ValidateShareRules validate = new ValidateShareRules(environment, "sevenGuest");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.sendRequest();

        TestReporter.assertTrue(validate.getFaultString().contains(faultString), "Verify that the fault string [" + validate.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(validate, AccommodationErrorCode.INVALID_PARTY_MIX_FOR_SHARE);

    }
}
