package com.disney.composite.api.tpsoModule.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.Create;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveGatedAdmission;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieveGatedAdmission {
	private String environment;
	private HouseHold hh;
	private String startDate;
	private String endDate;
	private String inactiveDate;
	private Create create;
	private RetrieveDetailsByTravelPlanId retrieveDetails;
	private RetrieveGatedAdmission retrieve;
	
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
		
		retrieveDetails = new RetrieveDetailsByTravelPlanId(environment, "Main");
		retrieveDetails.setTravelPlanId(create.getTravelPlanId());
		retrieveDetails.sendRequest();
		TestReporter.assertEquals(retrieveDetails.getResponseStatusCode(), "200", "An error occurred during retrieval.\nRequest:\n"+retrieveDetails.getRequest()+"\nResonse:\n"+retrieveDetails.getResponse());
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testRetrieveGatedAdmission(){
		retrieve = new RetrieveGatedAdmission(environment, "Main");
		retrieve.setGatedAdmissionId(retrieveDetails.getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieveDetails.getResponseStatusCode(), "200", "An error occurred during retrieval.\nRequest:\n"+retrieveDetails.getRequest()+"\nResonse:\n"+retrieveDetails.getResponse());
		TestReporter.assertTrue(XMLTools.getNodeList(XMLTools.makeXMLDocument(retrieve.getResponse()), "/Envelope/Body/retrieveGatedAdmissionResponse/retrieveGatedAdmissionResponse/gatedAdmissionDetails").getLength() > 0, "No gated admission details were found for the gated admission id ["+retrieveDetails.getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesId()+"] in ["+environment+"].");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}