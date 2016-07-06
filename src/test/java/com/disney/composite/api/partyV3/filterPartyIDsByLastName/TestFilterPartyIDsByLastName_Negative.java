package com.disney.composite.api.partyV3.filterPartyIDsByLastName;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyV3.operations.FilterPartyIDsByLastName;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestFilterPartyIDsByLastName_Negative extends BaseTest{
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "party", "partyV3", "negative"})
	public void missingName(){
		TestReporter.logScenario("Missing Name");
		FilterPartyIDsByLastName filter = new FilterPartyIDsByLastName(environment, "Main");
		filter.setLastName(BaseSoapCommands.REMOVE_NODE.toString());
		filter.sendRequest();
		TestReporter.logAPI(!filter.getFaultString().contains("Last Name is invalid  : Last Name is not found"), filter.getFaultString() ,filter);		
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3", "negative"})
	public void invalidPartyId(){
		TestReporter.logScenario("Invalid Party ID");
		String id = Randomness.randomString(4);
		FilterPartyIDsByLastName filter = new FilterPartyIDsByLastName(environment, "Main");
		filter.setPartyId(id);
		filter.sendRequest();
		TestReporter.logAPI(!filter.getFaultString().contains("Unmarshalling Error: For input string: \""+id+"\""), filter.getFaultString() ,filter);		
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3", "negative"})
	public void missingPartyId(){
		TestReporter.logScenario("Missing Party ID");
		FilterPartyIDsByLastName filter = new FilterPartyIDsByLastName(environment, "Main");
		filter.setPartyId(BaseSoapCommands.REMOVE_NODE.toString());
		filter.sendRequest();
		TestReporter.logAPI(!filter.getFaultString().contains("List of Party ids is null or empty : Party List is empty"), filter.getFaultString() ,filter);
	}
}