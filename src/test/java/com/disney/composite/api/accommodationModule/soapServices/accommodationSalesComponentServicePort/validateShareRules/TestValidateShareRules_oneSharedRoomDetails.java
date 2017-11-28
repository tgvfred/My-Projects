package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_oneSharedRoomDetails extends AccommodationBaseTest {

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
    public void testValidateShareRules_oneSharedRoomDetails() {

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.sendRequest();
        TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating share rules", validate);

        TestReporter.assertTrue(validate.getReturn().contains("true"), "Validate the return value is set to true as expected: [" + validate.getReturn() + "]");

        if (Environment.isSpecialEnvironment(environment)) {
            ValidateShareRules clone = (ValidateShareRules) validate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(validate, true), "Validating Response Comparison");
        }
    }
}
