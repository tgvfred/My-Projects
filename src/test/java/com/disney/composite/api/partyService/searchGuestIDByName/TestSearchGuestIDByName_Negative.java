package com.disney.composite.api.partyService.searchGuestIDByName;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyService.operations.SearchGuestIDByName;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByName_Negative  extends BaseTest{
	private Guest guest = new Guest();
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment);
	}
	
	@Test
	public void missingBothFirstAndLastName(){
		SearchGuestIDByName search = new SearchGuestIDByName(this.environment);
		search.setGuestFirstName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();

		TestReporter.logAPI(!search.getFaultString().contains("could not extract ResultSe"), search.getFaultString(), search);
	}
	
}
