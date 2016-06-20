package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.Create;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestCreate {
	private String environment;
	private HouseHold hh;
	private String startDate;
	private String endDate;
	private String inactiveDate;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testCreate(){
		startDate = Randomness.generateCurrentXMLDate(0);
		endDate = Randomness.generateCurrentXMLDate(1);
		inactiveDate = Randomness.generateCurrentXMLDate(4);
		generatHousehold();
		
		Create create = new Create(environment, "DayGuest");
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
		TestReporter.assertTrue(new Regex().match("[0-9]+", create.getTravelPlanId()), "The TP_ID ["+create.getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(new Regex().match("[0-9]+", create.getTravelPlanSegmentId()), "The TPS_ID ["+create.getTravelPlanId()+"] was not numeric as expected.");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}