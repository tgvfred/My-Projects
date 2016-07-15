package com.disney.composite.api.profileServicePort.retrieveProfilesByCode;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfiles;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfilesByCode;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveProfilesByCode_Negative extends BaseTest{
	protected GetOptions go;
	protected RetrieveProfiles retrieve;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByCode_Negative_InvalidCode(){
		TestReporter.logScenario("Invalid Code");
		String code = Randomness.randomString(4);
		RetrieveProfilesByCode retrieveByCode = new RetrieveProfilesByCode(environment);
		retrieveByCode.setProfileCode(code);
		retrieveByCode.sendRequest();
		TestReporter.logAPI(!retrieveByCode.getFaultString().contains("No profile found for profileId : ["+code+"]"), retrieveByCode.getFaultString() ,retrieveByCode);
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByCode_Negative_MissingCode(){
		TestReporter.logScenario("Missing Code");
		RetrieveProfilesByCode retrieveByCode = new RetrieveProfilesByCode(environment);
		retrieveByCode.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveByCode.sendRequest();
		TestReporter.logAPI(!retrieveByCode.getFaultString().contains("Unexpected Error occurred : retrieveProfilesByCode : org.hibernate.exception.SQLGrammarException: could not extract ResultSet : org.hibernate.exception.SQLGrammarException: could not extract ResultSet; nested exception is javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet"), retrieveByCode.getFaultString() ,retrieveByCode);
	}
}
