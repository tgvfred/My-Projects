package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_RO_CancelCheckedIn extends AccommodationBaseTest {

    private int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        int tries = 0;
        setEnvironment(environment);
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues(Environment.getBaseEnvironmentName(getEnvironment()));
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_RO_CancelCheckedIn() {
        TestReporter.logScenario("Test Cancel RO Checked IN negative");

        String faultString = "Accommodation should be in Booked status to be cancelled : null";

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, LiloResmErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS);

    }
}
