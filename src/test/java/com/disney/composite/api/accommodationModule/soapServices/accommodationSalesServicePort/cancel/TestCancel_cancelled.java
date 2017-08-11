package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_cancelled extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_AlreadyCancelled_Negative() {
        TestReporter.logScenario("Test Cancel Already Cancelled");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        String faultString = "Accommodation should be in Booked status to be cancelled : null";

        Cancel cancelTwo = new Cancel(environment, "Main");
        cancelTwo.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancelTwo.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancelTwo.sendRequest();

        TestReporter.assertTrue(cancelTwo.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancelTwo.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancelTwo, LiloResmErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS);

    }

}
