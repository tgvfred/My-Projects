package com.disney.composite.api.partyService.searchGuestIDByNameAndLocator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.PartyErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyService.operations.SearchGuestIDByNameAndLocator;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByNameAndLocator_Negative  extends BaseTest{
	private Guest guest = new Guest();
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
	}
	
	@Test
	public void missingLastName(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName("Testing");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setLocatorType("email");
		search.setLocatorValue(guest.primaryEmail().getEmail());
		search.sendRequest();

		validateApplicationError(search, PartyErrorCode.LAST_NAME_INVALID);
		TestReporter.logAPI(!search.getFaultString().contains("Last Name is invalid"), search.getFaultString(), search);

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByNameAndLocator", true);
		validateLogs(search, logItems);

	}

	@Test
	public void invalidLocatorType(){
		SearchGuestIDByNameAndLocator search = new SearchGuestIDByNameAndLocator(this.environment);
		search.setGuestFirstName("Testing");
		search.setGuestLastName("Testing");
		search.setLocatorType("invalid");
		search.setLocatorValue(guest.primaryEmail().getEmail());
		search.sendRequest();

		validateApplicationError(search, PartyErrorCode.LOCATOR_TYPE_INVALID);
		TestReporter.logAPI(!search.getFaultString().contains("Locator Type is not valid"), search.getFaultString(), search);

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByNameAndLocator", true);
		validateLogs(search, logItems);

	}
}