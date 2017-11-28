package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_oneSharedRoomDetails_nonDisneyProperty extends AccommodationBaseTest {

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
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules", "negative" })
    public void testValidateShareRules_oneSharedRoomDetails_nonDisneyProperty() {

        String faultString = "Property should be Disney : null";

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.setRoomTypeCode("DE");
        validate.setResortCode("DOL");
        validate.sendRequest();

        TestReporter.assertTrue(validate.getFaultString().contains(faultString), "Verify that the fault string [" + validate.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(validate, AccommodationErrorCode.PROPERTY_SHOULD_BE_DISNEY);

    }
}
