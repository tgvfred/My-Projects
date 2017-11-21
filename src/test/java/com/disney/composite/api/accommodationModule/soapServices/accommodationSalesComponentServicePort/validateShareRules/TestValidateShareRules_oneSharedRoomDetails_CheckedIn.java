package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_oneSharedRoomDetails_CheckedIn extends AccommodationBaseTest {

    private int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        int tries = 0;
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(2);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();
        checkingIn(Environment.getBaseEnvironmentName(getEnvironment()));

        CheckIn checkIn = new CheckIn(Environment.getBaseEnvironmentName(getEnvironment()), "UI_Booking");
        checkIn.setGuestId(getBook().getGuestId());
        checkIn.setLocationId(getLocationId());
        checkIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkIn.sendRequest();
        if (checkIn.getFaultString().contains("Row was updated or deleted by another transaction")) {
            maxTries = 5;
            tries = 0;
            do {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
                checkIn.sendRequest();
                tries++;
            } while (checkIn.getFaultString().contains("Row was updated or deleted by another transaction") && tries < maxTries);
        }
        TestReporter.assertTrue(checkIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getBook().getTravelPlanId() + "]: " + checkIn.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules" })
    public void testValidateShareRules_oneSharedRoomDetails_CheckedIn() {

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.setTravelStatus("Checked In");
        validate.sendRequest();
        TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating share rules", validate);

        TestReporter.assertTrue(validate.getReturn().contains("true"), "Validate the return value is set to true as expected: [" + validate.getReturn() + "]");

    }
}
