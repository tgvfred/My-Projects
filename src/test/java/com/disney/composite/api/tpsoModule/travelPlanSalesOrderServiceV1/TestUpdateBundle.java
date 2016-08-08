package com.disney.composite.api.tpsoModule.travelPlanSalesOrderServiceV1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.UpdateBundle;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestUpdateBundle {
	private String environment;
	private HouseHold hh;
	private HouseHold hh2;
	private Reservation reservation;
	private String TP_ID;
	private AddBundle add; 
	private RetrieveDetailsByTravelPlanId details;
	private int arrivalDaysOut = 0;
	private int departureDaysOut = 1;
	private RetrieveBundle retrieve;
	private UpdateBundle update;
	private String salesOrderId;
	private String packageBundleId;
	private String bookDate;
	private String startDate;
	private String endDate;
	private String firstGuestId;
	
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
		salesOrderId = add.getSalesOrderId();
				
		details.sendRequest();
		TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n"+details.getRequest()+"\nResonse:\n"+details.getResponse());
		
		retrieve = new RetrieveBundle(environment, "Main");
		retrieve.setPackageBundleId(details.getNonAccommodationPackageBundleDetailsId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nResponse:\n"+retrieve.getResponse()+"\nRequest:\n"+retrieve.getRequest());
		reportBundelDetails();
		details.sendRequest();
		TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n"+details.getRequest()+"\nResonse:\n"+details.getResponse());
		packageBundleId = details.getNonAccommodationPackageBundleDetailsId();
		bookDate = Randomness.generateCurrentXMLDate(arrivalDaysOut);
		startDate = Randomness.generateCurrentXMLDate(arrivalDaysOut);
		endDate =  Randomness.generateCurrentXMLDate(departureDaysOut);
		firstGuestId = retrieve.getGuestsId();
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testUpdateBundle(){
		update = new UpdateBundle(environment, "ModifyToNonReservationGuest");
		update.setTravelPlanId(TP_ID);
		update.setSalesOrderId(salesOrderId);
		update.setPackageBundleRequestsReferenceId(packageBundleId);
		update.setPackageBundleRequestsBookDate(bookDate);
		update.setPackageBundleRequestsStartDate(startDate);
		update.setPackageBundleRequestsEndDate(endDate);
		update.setPackageBundleRequestsSalesOrderItemGuestsGuestReferenceId("2");
		update.setGuestsGuestNameFirstName(hh.primaryGuest().getFirstName(), "1");
		update.setGuestsGuestNameLastName(hh.primaryGuest().getLastName(), "1");
		update.setGuestsGuestReferenceId("1", "1");
		update.setGuestsId(firstGuestId, "1");
		update.addNewGuest("2");
		update.setGuestsGuestNameFirstName(hh2.primaryGuest().getFirstName(), "2");
		update.setGuestsGuestNameLastName(hh2.primaryGuest().getLastName(), "2");
		update.setGuestsGuestReferenceId("2", "2");
		update.setGuestsId("0", "2");
		update.sendRequest();
		TestReporter.assertEquals(update.getResponseStatusCode(), "200", "An error occurred during updating.\nResponse:\n"+update.getResponse()+"\nRequest:\n"+update.getRequest());
		TestReporter.assertEquals(update.getUpdateBundleResponseTravelPlanId(), TP_ID, "The actual TP_ID ["+update.getUpdateBundleResponseTravelPlanId()+"] does not match that which is expected ["+TP_ID+"]");
		TestReporter.assertEquals(update.getUpdateBundleResponseSalesOrderId(), salesOrderId, "The actual Sales Order Id ["+update.getUpdateBundleResponseSalesOrderId()+"] does not match that which is expected ["+salesOrderId+"]");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
		
		hh2 = new HouseHold(1);
		hh2.sendToApi(environment);
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