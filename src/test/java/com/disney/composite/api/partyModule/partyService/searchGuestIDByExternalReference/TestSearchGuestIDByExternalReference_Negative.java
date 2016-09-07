package com.disney.composite.api.partyModule.partyService.searchGuestIDByExternalReference;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyModule.partyService.operations.SearchGuestIDByExternalReference;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestSearchGuestIDByExternalReference_Negative  extends BaseTest{	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){this.environment = environment;}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Negative_NoValuesNode(){
		TestReporter.logScenario("Search By External Reference Type and Value");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getFaultString().contains("Unexpected Error occurred : searchGuestIDByExternalReference : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), search.getFaultString() ,search);
		
		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", true);
		validateLogs(search, logItems);
	}
}