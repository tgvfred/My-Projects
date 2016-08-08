package com.disney.composite.api.tpsoModule.travelPlanSalesOrderServiceV1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestAddBundle {
	private String environment;
	private HouseHold hh;
	private Reservation reservation;
	private String TP_ID;
	private AddBundle add; 
	private RetrieveDetailsByTravelPlanId details;
	private int arrivalDaysOut = 0;
	private int departureDaysOut = 1;
	
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
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testAddBundle(){
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
		
		reportBundelDetails();
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
	
	private void reportBundelDetails(){
		TestReporter.logStep("Sales Order ID");
		TestReporter.log("Non-Accommodation Sales Order ID: " + details.getNonAccommodationBundleSalesOrderId());
		TestReporter.logStep("Non-Accommodation Package Bundle");
		TestReporter.log("Non-Accommodation Package Bundle Book Date: " + details.getNonAccommodationPackageBundleDetailsBookDate());
		TestReporter.log("Non-Accommodation Package Bundle Code: " + details.getNonAccommodationPackageBundleDetailsCode());
		TestReporter.log("Non-Accommodation Package Bundle Description: " + details.getNonAccommodationPackageBundleDetailsDescription());
		TestReporter.log("Non-Accommodation Package Bundle End Date: " + details.getNonAccommodationPackageBundleDetailsEndDate());
		TestReporter.log("Non-Accommodation Package Bundle Id: " + details.getNonAccommodationPackageBundleDetailsId());
		TestReporter.log("Non-Accommodation Package Bundle Name: " + details.getNonAccommodationPackageBundleDetailsName());
		TestReporter.log("Non-Accommodation Package Bundle Planned End Date: " + details.getNonAccommodationPackageBundleDetailsPlannedEndDate());
		TestReporter.log("Non-Accommodation Package Bundle Planned Start Date: " + details.getNonAccommodationPackageBundleDetailsPlannedStartDate());
		TestReporter.log("Non-Accommodation Package Bundle Room Only Flag: " + details.getNonAccommodationPackageBundleDetailsRoomOnlyFlag());
		TestReporter.log("Non-Accommodation Package Bundle Special Reservation Flag: " + details.getNonAccommodationPackageBundleDetailsSpecialReservationFlag());
		TestReporter.log("Non-Accommodation Package Bundle Start Date: " + details.getNonAccommodationPackageBundleDetailsStartDate());
		TestReporter.log("Non-Accommodation Package Bundle Status: " + details.getNonAccommodationPackageBundleDetailsStatus());
		TestReporter.logStep("Non-Accommodation Package Bundle Sales Order Guest");
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Guest Age Type: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemGuestsAgeType());
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Guest Guest Reference ID: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemGuestsGuestReferenceId());
		TestReporter.logStep("Non-Accommodation Package Bundle Sales Order Price Detail");
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Price Detail Currency Code: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailCurrencyCode());
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Price Detail Discount: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailDiscount());
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Price Detail Net: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailNet());
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Price Detail Price: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailPrice());
		TestReporter.log("Non-Accommodation Package Bundle Sales Order Price Detail Tax: " + details.getNonAccommodationPackageBundleDetailsSalesOrderItemPriceDetailTax());
		TestReporter.logStep("Non-Accommodation Package Bundle Component Details");
		TestReporter.log("Non-Accommodation Package Bundle Component Details Book Date: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsBookDate());
		TestReporter.log("Non-Accommodation Package Bundle Component Details End Date: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsEndDate());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Enterprise Product Id: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsEnterpriseProductId());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Id: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsId());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Name: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsName());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Product Id: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsProductId());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Start Date: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsStartDate());
		TestReporter.logStep("Non-Accommodation Package Bundle Component Details Sales Order Price Detail");
		TestReporter.log("Non-Accommodation Package Bundle Component Details Sales Order Price Detail Currency Code: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailCurrencyCode());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Sales Order Price Detail DetailDiscount: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailDiscount());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Sales Order Price Detail Net: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailNet());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Sales Order Price Detail Price: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailPrice());
		TestReporter.log("Non-Accommodation Package Bundle Component Details Sales Order Price Detail Tax: " + details.getNonAccommodationPackageBundleDetailsComponentDetailsSalesOrderItemPriceDetailTax());
	}
}