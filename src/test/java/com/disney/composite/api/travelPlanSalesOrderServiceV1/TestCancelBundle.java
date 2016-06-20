package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.CancelBundle;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveBundle;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestCancelBundle {
	private String environment;
	private HouseHold hh;
	private Reservation reservation;
	private String TP_ID;
	private AddBundle add; 
	private RetrieveDetailsByTravelPlanId details;
	private int arrivalDaysOut = 0;
	private int departureDaysOut = 1;
	private RetrieveBundle retrieve;
	private CancelBundle cancel;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){	
		try{reservation.cancelAccommodation();}catch(Exception e){}
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		
		generatHousehold();
		reservation = new GenerateReservation().bookResortReservation().BEACH_CLUB(environment);
		reservation.setNumberOfAdults(1);
		reservation.setArrivalDaysOut(arrivalDaysOut);
		reservation.setDepartureDaysOut(departureDaysOut);
		reservation.setGuestInfo(hh.primaryGuest());
		reservation.quickBook();
		TP_ID = reservation.getTravelPlanId();
		
		details = new RetrieveDetailsByTravelPlanId(environment, "Main");
		details.setTravelPlanId(TP_ID);
		details.sendRequest();
		TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n"+details.getRequest()+"\nResonse:\n"+details.getResponse());

		Sleeper.sleep(5000);
		try{add = new AddBundle(environment, "Main");}
		catch(IndexOutOfBoundsException e){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			add = new AddBundle(environment, "Main");
		}
		add.setGuestsGuestNameFirstName(hh.primaryGuest().getFirstName());
		add.setGuestsGuestNameLastName(hh.primaryGuest().getLastName());
		add.setGuestsGuestReferenceId(details.getGuestsId());
		add.setGuestsId(details.getGuestsId());
		add.setPackageBundleRequestsBookDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
//		add.setPackageBundleRequestsCode();
		add.setPackageBundleRequestsContactName(hh.primaryGuest().getFirstName() + " " + hh.primaryGuest().getLastName());
		add.setPackageBundleRequestsEndDate(Randomness.generateCurrentXMLDate(departureDaysOut));
		add.setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(details.getGuestsId());
		add.setPackageBundleRequestsStartDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
		add.setTravelPlanId(TP_ID);
		add.retrieveSalesOrderId(TP_ID);
		add.setSalesOrderId(add.getBundleSalesOrderIds()[0]);
		
		add.sendRequest();
		TestReporter.assertEquals(add.getResponseStatusCode(), "200", "An error occurred while adding a bundle.\nRequest:\n"+add.getRequest()+"\nResonse:\n"+add.getResponse());
		TestReporter.assertEquals(add.getTravelPlanId(), TP_ID, "The TP_ID ["+add.getTravelPlanId()+"] did not match that which was expected ["+TP_ID+"].");
		TestReporter.assertEquals(add.getSalesOrderId(), add.getBundleSalesOrderIds()[0], "The sales order ID ["+add.getSalesOrderId()+"] did not match that which was expected ["+add.getBundleSalesOrderIds()[0]+"].");
		
		details.sendRequest();
		TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n"+details.getRequest()+"\nResonse:\n"+details.getResponse());
		
		retrieve = new RetrieveBundle(environment, "Main");
		retrieve.setPackageBundleId(details.getNonAccommodationPackageBundleDetailsId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nResponse:\n"+retrieve.getResponse()+"\nRequest:\n"+retrieve.getRequest());
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testCancelBundle(){
		cancel = new CancelBundle(environment, "Main");
		cancel.setBundleId(retrieve.getPackageBundleDetailsId());
		cancel.setSalesOrderId(retrieve.getSalesOrderSummaryDetailsId());
		cancel.setTravelPlanId(retrieve.getTravelPlanId());
		cancel.sendRequest();
		TestReporter.assertEquals(cancel.getResponseStatusCode(), "200", "An error occurred during cancellation.\nResponse:\n"+cancel.getResponse()+"\nRequest:\n"+cancel.getRequest());
		TestReporter.assertEquals(cancel.getResponseTravelPlanId(), retrieve.getTravelPlanId(), "The actual travel plan ID ["+cancel.getResponseTravelPlanId()+"] did not match the expected travel plan ID ["+retrieve.getTravelPlanId()+"].");
		TestReporter.assertEquals(cancel.getResponseSalesOrderId(), retrieve.getSalesOrderSummaryDetailsId(), "The actual sales order ID ["+cancel.getResponseSalesOrderId()+"] did not match the expected sales order ID ["+retrieve.getSalesOrderSummaryDetailsId()+"].");
		
		TestReporter.log("Package Bundle ID: " + retrieve.getPackageBundleDetailsId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nResponse:\n"+retrieve.getResponse()+"\nRequest:\n"+retrieve.getRequest());
		TestReporter.assertEquals(retrieve.getPackageBundleDetailsStatus(), "Cancelled", "The package bundle status ["+retrieve.getPackageBundleDetailsStatus()+"] was not [Cancelled] as expected.");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}