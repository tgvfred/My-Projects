package com.disney.composite.api.partyV3.searchGuestIDByExternalReference;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.partyV3.operations.SearchGuestIDByExternalReference;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByExternalReference  extends BaseTest{
	private Guest guest = new Guest();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = "Development";
		guest.sendToApi(this.environment, true);
	}
	
	@Test
	public void searchGuestIDByEmail(){
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", guest.getOdsId());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}
	
}
