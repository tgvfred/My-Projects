package com.disney.composite.api.diningModule.scheduledEventsServicePort;
//package com.disney.composite.api.diningModule.scheduledEventsServicePort;
//
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.Checkout;
//import com.disney.utils.TestReporter;
//import com.disney.utils.dataFactory.staging.bookSEReservation.PreReqBooking;
//import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
//
//public class TestCheckout {
//	// Defining global variables
//	protected String testName = null;
//	protected String environment = null;
//	
//	@BeforeMethod(alwaysRun = true)
//	@Parameters({ "environment" })
//	public void setup(String environment) {
//		this.environment = environment;
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
//	public void testCheckout(){
//		TestReporter.logStep("Book PreRequisite Reservation");
//		PreReqBooking res = new PreReqBooking();
//		ScheduledEventReservation book = res.bookEventDining(environment);
//		res.markArrived(book);
//		
//		TestReporter.logStep("Checkout reservation");
//		Checkout checkout = new Checkout(environment);
//		checkout.setTravelPlanSegmentId(book.getConfirmationNumber());
//		checkout.sendRequest();
//		TestReporter.logAPI(!checkout.getResponseStatusCode().equals("200"), "An error occurred checking-out an event dining service reservation", checkout);
//		TestReporter.assertTrue(checkout.isSuccessfullyCheckedOut(), "Failed to successfully checkout reservation ["+book.getConfirmationNumber()+"].");
//	}
//}