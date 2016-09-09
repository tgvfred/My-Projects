package com.disney.composite.api.RestServices.travelPlan.travelPlanService.modifyGuests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.ModifyGuestsRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestModifyGuests {
private String environment = "Bashful";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
		//this.environment = environment;
		this.environment = "Bashful";
	}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 */
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_SingleAddress () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
		
		//Add needed data for submission
		request.getGuestReferenceDetail().get(0).setAge("0");
		request.getGuestReferenceDetail().get(0).setAgeType("ADULT");
		request.getGuestReferenceDetail().get(0).getGuest().setFirstName("Quick22");
		request.getGuestReferenceDetail().get(0).getGuest().setLastName("Book22");
		request.getGuestReferenceDetail().get(0).getGuest().setPartyId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setGuestLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setLocatorUseType("PERSONAL");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setPrimary("false");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setAddressLine1("Address1");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setAddressLine2("Address2");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setCity("Orlando");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setCountry("USA");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setState("FL");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setPostalCode("32849");
		request.getGuestReferenceDetail().get(0).getGuest().setDoNotMailIndicator("false");
		request.getGuestReferenceDetail().get(0).getGuest().setDoNotPhoneIndicator("false");
		request.getGuestReferenceDetail().get(0).getGuest().setPreferredLanguage("eng");
		request.getGuestReferenceDetail().get(0).getGuest().setDclGuestId("0");
		request.getGuestReferenceDetail().get(0).getGuest().setGuestId("238497533");
		request.getGuestReferenceDetail().get(0).getGuest().setActive("true");
		request.getGuestReferenceDetail().get(0).setPurposeOfVisit("Leisure");
		request.getGuestReferenceDetail().get(0).setRole("Guest");
		request.getGuestReferenceDetail().get(0).setCorrelationID("0");
		
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	}	
	@Test(groups={"api","rest", "regression", "travelPlan", "travelPlanService", "modifyGuests"})
	public void testmodifyGuests_TwoAddress () throws IOException{
		// set log levels for debugging
		TestReporter.setDebugLevel(1);
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		
		//create new request file
		ModifyGuestsRequest request = new ModifyGuestsRequest();
		
		//Add needed data for submission
		request.getGuestReferenceDetail().get(0).setAge("0");
		request.getGuestReferenceDetail().get(0).setAgeType("ADULT");
		request.getGuestReferenceDetail().get(0).getGuest().setFirstName("Quick22");
		request.getGuestReferenceDetail().get(0).getGuest().setLastName("Book22");
		request.getGuestReferenceDetail().get(0).getGuest().setPartyId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setGuestLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setLocatorUseType("PERSONAL");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setPrimary("false");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setAddressLine1("Address1");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setAddressLine2("Address2");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setCity("Orlando");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setCountry("USA");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setState("FL");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(0).setPostalCode("32849");
		request.getGuestReferenceDetail().get(0).getGuest().addressDetails();
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setGuestLocatorId("0");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setLocatorUseType("BILL TO");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setPrimary("false");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setAddressLine1("Address1");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setAddressLine2("Address2");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setCity("Orlando");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setCountry("USA");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setState("FL");
		request.getGuestReferenceDetail().get(0).getGuest().getAddressDetails().get(1).setPostalCode("32849");
		request.getGuestReferenceDetail().get(0).getGuest().setDoNotMailIndicator("false");
		request.getGuestReferenceDetail().get(0).getGuest().setDoNotPhoneIndicator("false");
		request.getGuestReferenceDetail().get(0).getGuest().setPreferredLanguage("eng");
		request.getGuestReferenceDetail().get(0).getGuest().setDclGuestId("0");
		request.getGuestReferenceDetail().get(0).getGuest().setGuestId("238497533");
		request.getGuestReferenceDetail().get(0).getGuest().setActive("true");
		request.getGuestReferenceDetail().get(0).setPurposeOfVisit("Leisure");
		request.getGuestReferenceDetail().get(0).setRole("Guest");
		request.getGuestReferenceDetail().get(0).setCorrelationID("0");
		
		//Submit request
		RestResponse response= Rest.travelPlan(environment).travelPlanService().modifyGuests().sendPutRequest(request);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	}
}
