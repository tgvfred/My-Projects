package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestCancel_addAccomm_checkInOne_cancelOne_tcgOnly extends AccommodationBaseTest {

    String firstTCG;
    String firstTPS;
    String firstTC;
    String firstTP;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        setIsADA(false);
        bookReservation();

        firstTCG = getBook().getTravelComponentGroupingId();
        firstTPS = getBook().getTravelPlanSegmentId();
        firstTC = getBook().getTravelComponentId();
        firstTP = getBook().getTravelPlanId();

        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(firstTP);
        getBook().setTravelPlanSegementId(firstTPS);
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a new TPS with an existing TP: " + getBook().getFaultString(), getBook());

        CheckInHelper checkInHelper = new CheckInHelper(Environment.getBaseEnvironmentName(getEnvironment()), getBook());
        checkInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel", "tpv3" })
    public void testCancel_addAccomm_checkInOne_cancelOne_tcgOnly() {
        TestReporter.logScenario("Test Cancel RO ADA");

        String faultString = " Accommodation should be in Booked status to be cancelled :";
        Cancel cancel = new Cancel(environment, "MainCancel");
        cancel.setCancelDate(BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getFaultString().contains(faultString), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_CANCELLED);
    }
}
