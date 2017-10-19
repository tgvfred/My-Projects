package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrievePostedCancellationFee_TPS_checkedIn extends AccommodationBaseTest {

    private int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {

        int tries = 0;
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(1);
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_checkedIn() {

        String faultString = "cannot calculate Cancel fee : Cannot Calculate Cancellation Fee for cancelled or checked in or checked out reservation";

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "Main");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().contains(faultString), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.CANNOT_CALCULATE_CANCEL_FEE);
    }
}
