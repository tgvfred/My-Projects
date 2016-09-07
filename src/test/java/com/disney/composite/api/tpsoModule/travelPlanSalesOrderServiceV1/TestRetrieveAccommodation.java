package com.disney.composite.api.tpsoModule.travelPlanSalesOrderServiceV1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveAccommodation;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestRetrieveAccommodation {
	private String environment;
	private HouseHold hh;
	private Reservation reservation;
	private String TP_ID;
	private RetrieveAccommodation retrieve; 
	private String[] accommSalesOrderItemIds;
	private RetrieveDetailsByTravelPlanId details;
	
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
		reservation.setGuestInfo(hh.primaryGuest());
		reservation.quickBook();
		TP_ID = reservation.getTravelPlanId();
		
		details = new RetrieveDetailsByTravelPlanId(environment, "Main");
		details.setTravelPlanId(TP_ID);
		details.sendRequest();
		TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n"+details.getRequest()+"\nResonse:\n"+details.getResponse());
		
		retrieve = new RetrieveAccommodation(environment, "Main");
		retrieve.retrieveAccommodationSalesOrderItemIds(TP_ID);
		accommSalesOrderItemIds = retrieve.getAccommodationSalesOrderItemIds();
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveAccommodation(){
		retrieve.setAccommodationId(accommSalesOrderItemIds[0]);
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred while retrieveing the accommodation(s).\nRequest:\n"+retrieve.getRequest()+"\nResonse:\n"+retrieve.getResponse());
		TestReporter.assertEquals(retrieve.getSalesOrderSummaryId(), details.getSalesOrdersId(), "The sales order id ["+retrieve.getSalesOrderSummaryId()+"] did not match the expected id ["+details.getSalesOrdersId()+"].");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}