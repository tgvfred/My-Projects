package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.Create;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieveDetailsByTravelPlanId {
	private String environment;
	private HouseHold hh;
	private String startDate;
	private String endDate;
	private String inactiveDate;
	private Create create;
	private RetrieveDetailsByTravelPlanId retrieve;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;

		startDate = Randomness.generateCurrentXMLDate(0);
		endDate = Randomness.generateCurrentXMLDate(1);
		inactiveDate = Randomness.generateCurrentXMLDate(4);
		generatHousehold();
		
		create = new Create(environment, "DayGuest");
		create.setSalesOrderArrivalDate(startDate);
		create.setSalesOrderDepartureDate(endDate);
		create.setSalesOrderGatedAdmissionRequestsEndDate(endDate);
		create.setSalesOrderGatedAdmissionRequestsStartDate(startDate);
		create.setTravelPlanRequestEndDate(endDate);
		create.setTravelPlanRequestStartDate(startDate);
		create.setSalesOrderGatedAdmissionRequestsSalesOrderItemCommentsRequestInactiveDate(inactiveDate);
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setGuestAddressCity(hh.primaryGuest().primaryAddress().getCity());
		create.setGuestAddressCountry(hh.primaryGuest().primaryAddress().getCountry());
		create.setGuestAddressLine1(hh.primaryGuest().primaryAddress().getAddress1());
		create.setGuestAddressRegionName(hh.primaryGuest().primaryAddress().getState());
		create.setGuestAddressStateCode(hh.primaryGuest().primaryAddress().getStateAbbv());
		create.setGuestAddressZipCode(hh.primaryGuest().primaryAddress().getZipCode());
		create.sendRequest();
		TestReporter.assertEquals(create.getResponseStatusCode(), "200", "An error occurred during creation.\nRequest:\n"+create.getRequest()+"\nResonse:\n"+create.getResponse());
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveDetailsByTravelPlanId(){
		retrieve = new RetrieveDetailsByTravelPlanId(environment, "Main");
		retrieve.setTravelPlanId(create.getTravelPlanId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nRequest:\n"+retrieve.getRequest()+"\nResonse:\n"+retrieve.getResponse());
		TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.getSalesOrdersSalesOrderItemGroupsId()), "The Sales Order Item Group Id ["+retrieve.getSalesOrdersSalesOrderItemGroupsId()+"] was not numeric as expected.");
		TestReporter.assertEquals(retrieve.getSalesOrdersId(), create.getSalesOrderIdId(), "The Sales Order Id ["+retrieve.getSalesOrdersId()+"] was not that which was expected ["+create.getSalesOrderIdId()+"].");
		TestReporter.assertEquals(retrieve.getTravelPlanTravelPlanId(), create.getTravelPlanId(), "The travel plan ["+retrieve.getTravelPlanTravelPlanId()+"] was not that which was expected ["+create.getTravelPlanId()+"]");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}