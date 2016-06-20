package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveBundle;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestRetrieveBundle {
	private String environment;
	private HouseHold hh;
	private Reservation reservation;
	private String TP_ID;
	private AddBundle add; 
	private RetrieveDetailsByTravelPlanId details;
	private int arrivalDaysOut = 0;
	private int departureDaysOut = 1;
	private RetrieveBundle retrieve;
	private String detailsPackageBundleCode;
	
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
		detailsPackageBundleCode = details.getNonAccommodationPackageBundleDetailsCode();
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveBundle(){
		retrieve = new RetrieveBundle(environment, "Main");
		retrieve.setPackageBundleId(details.getNonAccommodationPackageBundleDetailsId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nResponse:\n"+retrieve.getResponse()+"\nRequest:\n"+retrieve.getRequest());
		TestReporter.assertEquals(retrieve.getPackageBundleDetailsCode(), detailsPackageBundleCode, "The expected package bundle details code ["+detailsPackageBundleCode+"] did not match the actual package bundle details code ["+retrieve.getPackageBundleDetailsCode()+"].");
		TestReporter.assertEquals(retrieve.getTravelPlanId(), add.getTravelPlanId(), "The expected travel plan ID ["+retrieve.getTravelPlanId()+"] did not match the actual ptravel plan ID ["+add.getTravelPlanId()+"].");
		reportBundelDetails();
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
	
	private void reportBundelDetails(){
		TestReporter.logStep("Retrieve Details");
		TestReporter.log("Guest External Reference ID ID: " + retrieve.getGuestsExternalReferenceIdsId());
		TestReporter.log("Guest External Reference ID Type: " + retrieve.getGuestsExternalReferenceIdsType());
		TestReporter.log("Guest Guest Reference ID: " + retrieve.getGuestsGuestReferenceId());
		TestReporter.log("Guest ID: " + retrieve.getGuestsId());
		TestReporter.log("Package Bundle Details Code: " + retrieve.getPackageBundleDetailsCode());
		TestReporter.log("Package Bundle Details Component Details Enterprise ID: " + retrieve.getPackageBundleDetailsComponentDetailsEnterpriseId());
		TestReporter.log("Package Bundle Details Component Details ID: " + retrieve.getPackageBundleDetailsComponentDetailsId());
		TestReporter.log("Package Bundle Details Component Details Name: " + retrieve.getPackageBundleDetailsComponentDetailsName());
		TestReporter.log("Package Bundle Details Component Details Product ID: " + retrieve.getPackageBundleDetailsComponentDetailsProductId());
		TestReporter.log("Package Bundle Details Description: " + retrieve.getPackageBundleDetailsDescription());
		TestReporter.log("Package Bundle Details ID: " + retrieve.getPackageBundleDetailsId());
		TestReporter.log("Package Bundle Details Name: " + retrieve.getPackageBundleDetailsName());
		TestReporter.log("Package Bundle Details Status: " + retrieve.getPackageBundleDetailsStatus());
		TestReporter.log("Sales Order Summary Details ID: " + retrieve.getSalesOrderSummaryDetailsId());
		TestReporter.log("Sales Order Summary Details Primary Guest Refrence ID: " + retrieve.getSalesOrderSummaryDetailsPrimaryGuestResferenceId());
		TestReporter.log("Sales Order Summary Details Source Accounting Center ID: " + retrieve.getSalesOrderSummaryDetailsSourceAccountingCenterId());
		TestReporter.log("Sales Order Summary Details Status: " + retrieve.getSalesOrderSummaryDetailsStatus());
		TestReporter.log("Travel Plan ID: " + retrieve.getTravelPlanId());
	}
}