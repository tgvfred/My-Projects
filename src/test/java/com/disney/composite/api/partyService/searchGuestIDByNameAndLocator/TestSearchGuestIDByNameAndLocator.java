package com.disney.composite.api.partyService.searchGuestIDByNameAndLocator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyService.operations.SearchGuestIDByNameAndLocator;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByNameAndLocator  extends BaseTest{
	private Guest guest = new Guest();
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment);
		
	}
	
	@Test
	public void searchGuestIDByFullNameAndEMail(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("email");
		search.setLocatorValue(guest.primaryEmail().getEmail());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}

	@Test
	public void searchGuestIDByLastNameAndEMail(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("email");
		search.setLocatorValue(guest.primaryEmail().getEmail());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}


	@Test
	public void searchGuestIDByFullNameAndPhone(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("phone");
		search.setLocatorValue(guest.primaryPhone().getNumber());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}

	@Test
	public void searchGuestIDByLastNameAndPhone(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("phone");
		search.setLocatorValue(guest.primaryPhone().getNumber());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}

	@Test
	public void searchGuestIDByFullNameAndAddress(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("address");
		search.setLocatorValue(guest.primaryAddress().getZipCode());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}

	@Test
	public void searchGuestIDByLastNameAndAddress(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(guest.getLastName());
		search.setLocatorType("Address");
		search.setLocatorValue(guest.primaryAddress().getZipCode());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	

	@Test
	public void nothingReturned(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName("BLAH");
		search.setLocatorType("Address");
		search.setLocatorValue("BLAH");
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "The Party ID's is not 0 as expected.");
		
	}
}
