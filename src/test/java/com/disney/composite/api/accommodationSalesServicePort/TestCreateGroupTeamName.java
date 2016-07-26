package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateGroupTeamName {
	private String environment = "";
	
	@BeforeClass(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateGroupTeamName"})
	public void testCreateGroupTeamName_MainFlow(){
		TestReporter.logScenario("Test Create a Group Team Name");
		CreateGroupTeamName CreateGroupTeamName = new CreateGroupTeamName(environment, "createGroupTeamName" );
		CreateGroupTeamName.setgroupTeamName("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupcode("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupTeamViewTO("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.sendRequest();
		TestReporter.logAPI(!CreateGroupTeamName.getResponseStatusCode().equals("200"), "An error occurred creating a group team name", CreateGroupTeamName);
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupCode(), "The response contains a Group Code");
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupTeamId(), "The response contains a GroupTeam Id");
	}
}
