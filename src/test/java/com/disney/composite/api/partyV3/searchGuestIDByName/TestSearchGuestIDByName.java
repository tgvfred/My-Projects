package com.disney.composite.api.partyV3.searchGuestIDByName;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.partyV3.operations.SearchGuestIDByName;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByName  extends BaseTest{
	private Guest guest = new Guest();
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment, true);
	}
	
	@Test
	public void searchGuestIDByName(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName(guest.getFirstName());
		search.setGuestLastName(guest.getLastName());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	
}
