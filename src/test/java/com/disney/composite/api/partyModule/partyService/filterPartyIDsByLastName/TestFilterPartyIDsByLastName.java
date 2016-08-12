package com.disney.composite.api.partyModule.partyService.filterPartyIDsByLastName;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.partyModule.partyService.operations.CreateAndRetrieveParty;
import com.disney.api.soapServices.partyModule.partyService.operations.FilterPartyIDsByLastName;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestFilterPartyIDsByLastName extends BaseTest{
	private Guest guest = new Guest();	// 
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testFilterPartyIDsByLastName_SingleParty(){
		TestReporter.logScenario("Single Party");
		CreateAndRetrieveParty party = createParty();
		
		FilterPartyIDsByLastName filter = filterPartyIds(party);
		filter.sendRequest();
		TestReporter.logAPI(!filter.getResponseStatusCode().equals("200"), "An error occurred filtering party ids by last name.", filter);
		TestReporter.assertTrue(verifyPartyIdInResponse(filter.getResponsePartyIds(), party.getPartyid()), "Verify the party ID ["+party.getPartyid()+"] is found in the response.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "filterPartyIDsByLastName", false);
		validateLogs(filter, logItems);
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testFilterPartyIDsByLastName_MultiplePartiesSameLastName(){
		TestReporter.logScenario("Multiple Parties, Same Last Name");
		CreateAndRetrieveParty party = createParty();
		CreateAndRetrieveParty party2 = createParty();
		
		FilterPartyIDsByLastName filter = filterPartyIds(party);
		filter.addPartyIdNodeAndSetValue(party2.getPartyid());
		filter.sendRequest();
		TestReporter.logAPI(!filter.getResponseStatusCode().equals("200"), "An error occurred filtering party ids by last name.", filter);
		TestReporter.assertTrue(verifyPartyIdInResponse(filter.getResponsePartyIds(), party.getPartyid()), "Verify the party ID ["+party.getPartyid()+"] is found in the response.");
		TestReporter.assertTrue(verifyPartyIdInResponse(filter.getResponsePartyIds(), party2.getPartyid()), "Verify the party ID ["+party2.getPartyid()+"] is found in the response.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "filterPartyIDsByLastName", false);
		validateLogs(filter, logItems);
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testFilterPartyIDsByLastName_MultipleParties_NoResults(){
		TestReporter.logScenario("Multiple Parties, No Results");
		
		Guest guest = new Guest();
		guest.sendToApi(this.environment);
		CreateAndRetrieveParty party = new CreateAndRetrieveParty(environment, "PrimaryGuestOds");
		party.setPrimaryGuestLastName(guest.getLastName());
		party.sendRequest();
		TestReporter.logAPI(!party.getResponseStatusCode().equals("200"), "An error occurred creating the party.", party);
		
		Guest guest2 = new Guest();
		guest2.sendToApi(this.environment);
		CreateAndRetrieveParty party2 = new CreateAndRetrieveParty(environment, "PrimaryGuestOds");
		party2.setPrimaryGuestLastName(guest2.getLastName());
		party2.sendRequest();
		TestReporter.logAPI(!party2.getResponseStatusCode().equals("200"), "An error occurred creating the party.", party2);
		
		FilterPartyIDsByLastName filter = new FilterPartyIDsByLastName(environment, "Main");
		filter.setLastName(guest.getLastName());
		filter.setPartyId(party2.getPartyid());
		filter.sendRequest();
		TestReporter.logAPI(!filter.getResponseStatusCode().equals("200"), "An error occurred filtering party ids by last name.", filter);
		TestReporter.assertTrue(filter.getNumberOfResponsePartyIds() == 0, "Party IDs were found in the response when they were not expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "filterPartyIDsByLastName", false);
		validateLogs(filter, logItems);
	}
	
	/**
	 * Generate a new FilterPartyIDsByLastName object
	 * @param party - predefined party to be used with the filter
	 * @return new FilterPartyIDsByLastName object
	 */
	private FilterPartyIDsByLastName filterPartyIds(CreateAndRetrieveParty party){
		FilterPartyIDsByLastName filter = new FilterPartyIDsByLastName(environment, "Main");
		filter.setLastName(guest.getLastName());
		filter.setPartyId(party.getPartyid());
		return filter;
	}
	/**
	 * Generate a new CreateAndRetrieveParty object
	 * @return new CreateAndRetrieveParty object
	 */
	private CreateAndRetrieveParty createParty(){
		CreateAndRetrieveParty party = new CreateAndRetrieveParty(environment, "PrimaryGuestOds");
		party.setPrimaryGuestLastName(guest.getLastName());
		party.sendRequest();
		TestReporter.logAPI(!party.getResponseStatusCode().equals("200"), "An error occurred creating the party.", party);
		return party;
	}
	/**
	 * Verifies a party ID is found in the SOAP response
	 * @param partyIds - list of party IDs in the response
	 * @param id - specific party ID for which to search
	 * @return - boolean, true if the party ID is found, false otherwise
	 */
	private boolean verifyPartyIdInResponse(NodeList partyIds, String id){
		boolean idFound = false;
		for(int i = 0; i < partyIds.getLength(); i++){
			if(partyIds.item(i).getTextContent().equals(id)){
				idFound = true;
				break;
			}
		}
		return idFound;
	}
}
