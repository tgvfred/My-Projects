package com.disney.composite.api.partyV3.searchGuestIDByExternalReference;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyV3.operations.SearchGuestIDByExternalReference;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestSearchGuestIDByExternalReference_Negative  extends BaseTest{	
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Negative_InvalidValue(){
		String value = Randomness.randomString(4);
		TestReporter.logScenario("Search By External Reference Type and Value");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", value);
		search.sendRequest();
		TestReporter.logAPI(!search.getFaultString().contains("Unmarshalling Error: For input string: \""+value+"\""), search.getFaultString() ,search);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Negative_NoValuesNode(){
		TestReporter.logScenario("Search By External Reference Type and Value");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getFaultString().contains("Unexpected Error occurred : searchGuestIDByExternalReference : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), search.getFaultString() ,search);
	}
}