package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.Checkout;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookDiningReservation.PreReqBooking;
import com.disney.utils.dataFactory.staging.bookDiningReservation.ScheduledEventReservation;

public class TestCheckout {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment.set(environment);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testCheckout(){
		TestReporter.logStep("Book PreRequisite Reservation");
		PreReqBooking res = new PreReqBooking();
		ScheduledEventReservation book = res.bookEventDining(environment.get());
		res.markArrived(book);
		
		TestReporter.logStep("Checkout reservation");
		Checkout checkout = new Checkout(environment.get());
		checkout.setTravelPlanSegmentId(book.getConfirmationNumber());
		checkout.sendRequest();
		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking-out an event dining service reservation", checkout);
		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Failed to successfully checkout reservation ["+book.getConfirmationNumber()+"].");
	}
}