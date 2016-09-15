package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTeamsByGroupCode;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveTeamByGroupCode {
private String environment = "";
	
@BeforeClass(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
	public void testRetrieveTeamsByGroupCode_MainFlow(){
		
		RetrieveTeamsByGroupCode RetrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
		RetrieveTeamsByGroupCode.setgroupcode("Donald"+Randomness.randomAlphaNumeric(4));
		RetrieveTeamsByGroupCode.sendRequest();
		System.out.println(RetrieveTeamsByGroupCode.getRequest());
		System.out.println(RetrieveTeamsByGroupCode.getResponse());
		TestReporter.assertEquals(RetrieveTeamsByGroupCode.getResponseStatusCode(), "200", "The response code was not 200");
	}
	
	
	
}