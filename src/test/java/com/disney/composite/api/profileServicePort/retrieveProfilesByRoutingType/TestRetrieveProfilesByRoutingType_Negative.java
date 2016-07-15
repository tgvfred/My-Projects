package com.disney.composite.api.profileServicePort.retrieveProfilesByRoutingType;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfilesByRoutingType;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveProfilesByRoutingType_Negative extends BaseTest{
	
	@Override
	@BeforeTest
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_Negative_invalidRoutingType(){
		TestReporter.logScenario("Retrieve Profiles By Routing Type, and Invalid Profile Code and Profile");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = new RetrieveProfilesByRoutingType(environment);
		retrieveProfilesByRoutingType.setRoutingType(Randomness.randomString(4));
		retrieveProfilesByRoutingType.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.setProfileType(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.setIncludeInactiveProfiles(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.sendRequest();
		TestReporter.logAPI(!retrieveProfilesByRoutingType.getFaultString().contains("Routing type is missing : Invalid Request! Either the request is NULL or its missing a valid RoutingType!"), retrieveProfilesByRoutingType.getFaultString() ,retrieveProfilesByRoutingType);
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_Negative_missingRoutingType(){
		TestReporter.logScenario("Retrieve Profiles By Routing Type, and Invalid Profile Code and Profile");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = new RetrieveProfilesByRoutingType(environment);
		retrieveProfilesByRoutingType.setRoutingType(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.setProfileType(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.setIncludeInactiveProfiles(BaseSoapCommands.REMOVE_NODE.toString());
		retrieveProfilesByRoutingType.sendRequest();
		TestReporter.logAPI(!retrieveProfilesByRoutingType.getFaultString().contains("Routing type is missing : Invalid Request! Either the request is NULL or its missing a valid RoutingType!"), retrieveProfilesByRoutingType.getFaultString() ,retrieveProfilesByRoutingType);
	}
}