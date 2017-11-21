package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_oneSharedRoomDetails_CheckingIn extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(2);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();
        checkingIn(Environment.getBaseEnvironmentName(getEnvironment()));
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules" })
    public void testValidateShareRules_oneSharedRoomDetails_CheckingIn() {

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.setTravelStatus("Checking In");
        validate.sendRequest();
        TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating share rules", validate);

        TestReporter.assertTrue(validate.getReturn().contains("true"), "Validate the return value is set to true as expected: [" + validate.getReturn() + "]");

    }
}
