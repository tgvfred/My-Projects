package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.Create;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.Search;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestSearch {
	private String environment;
	private HouseHold hh;
	private String startDate;
	private String endDate;
	private String inactiveDate;
	private Create create;
	private Search search;
	private String expectedSalesOrderId;
	
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
		expectedSalesOrderId = create.getSalesOrderIdId();
		Sleeper.sleep(5000);
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testSearch(){
		
		search = new Search(environment, "ReservationSearch-LastName_Startdate");
		search.setGuestLastName(create.getGuestLastName());
		search.setStartDate(startDate);
		search.sendRequest();
		TestReporter.assertEquals(search.getResponseStatusCode(), "200", "An error occurred during the search.\nRequest:\n"+search.getRequest()+"\nResponse:\n"+search.getResponse());
		TestReporter.assertTrue(salesOrderIdContainedInResponse(search.getTravelPlanSalesOrderIds(), expectedSalesOrderId), "The sales order id ["+search.getTravelPlanSalesOrderId()+"] was not found in the list of expected sales order IDs ["+getAllSalesOrderId(search.getTravelPlanSalesOrderIds())+"].");
	}
	
	private boolean salesOrderIdContainedInResponse(String[] ids, String id){
		System.out.println();
		boolean contained = false;
		for(int i = 0; i < ids.length; i++)
			if(ids[i].equals(id)){
				contained = true;
				break;
			}
		return contained;
	}
	
	private String getAllSalesOrderId(String[] ids){
		String idList = "";
		for(int i = 0; i < ids.length; i++){
			idList = idList + ids[i] + ";";
		}
		idList = idList.substring(0, idList.lastIndexOf(";"));
		return idList;
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}