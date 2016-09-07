package com.disney.composite.api.partyModule.partyV3.searchGuestIDByEmail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyModule.partyV3.operations.SearchGuestIDByEmail;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByEmail  extends BaseTest{
	private Guest guest = new Guest();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment, true);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void searchGuestIDByEmail(){
		SearchGuestIDByEmail search = new SearchGuestIDByEmail(this.environment);
		search.setGuestEmail(guest.primaryEmail().getEmail());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void searchGuestIDByEmail_NoEmail(){
		SearchGuestIDByEmail search = new SearchGuestIDByEmail(this.environment);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void searchGuestIDByEmail_NoEmailNode(){
		SearchGuestIDByEmail search = new SearchGuestIDByEmail(this.environment);
		search.setGuestEmail(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected");
	}
}