package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateGroupTeamName {
private String environment = "";
	
	@BeforeTest(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateGroupTeamName"})
	public void testCreateGroupTeamName_MainFlow(){
		
		CreateGroupTeamName CreateGroupTeamName = new CreateGroupTeamName(environment, "createGroupTeamName" );
		CreateGroupTeamName.setgroupTeamName("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupcode("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupTeamViewTO("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.sendRequest();
		System.out.println(CreateGroupTeamName.getRequest());
		System.out.println(CreateGroupTeamName.getResponse());
		TestReporter.assertEquals(CreateGroupTeamName.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupCode(), "The response contains a Group Code");
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupTeamId(), "The response contains a GroupTeam Id");
	}
}
