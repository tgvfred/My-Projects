package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_AddBundle extends AccommodationBaseTest{
		
	@Override
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        daysOut.set(Randomness.randomNumberBetween(1, 12));
        nights.set(Randomness.randomNumberBetween(1, 3));
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        setIsBundle(true);
        bookReservation();
    }
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "Cancel"})
	public void testCancel_AddBundle(){
		TestReporter.logScenario("Test Cancel");
	    
		Cancel cancel = new Cancel(environment, "Main");
		cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
		cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
		TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
	}
}
