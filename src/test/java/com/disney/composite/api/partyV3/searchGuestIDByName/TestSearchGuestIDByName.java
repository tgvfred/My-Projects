package com.disney.composite.api.partyV3.searchGuestIDByName;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyV3.operations.SearchGuestIDByName;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByName  extends BaseTest{
	private Guest guest = new Guest();
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment, true);
		
	}
	
	@Test
	public void searchGuestIDByFullName(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(guest.getLastName());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	

	@Test
	public void searchGuestIDByFirstName(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}


	@Test
	public void searchGuestIDByLastName(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(guest.getLastName());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() >= 1, "The Party ID's is not at least 1 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	

	@Test
	public void searchGuestIDByInvalidNames(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName("MissingName");
		search.setGuestLastName("InvalidName");
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "The Party ID's is not 0 as expected. It is ["+search.getNumberOfResponsePartyIds()+"]");
	}
}
