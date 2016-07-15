package com.disney.composite.api.organizationServiceV2;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.organizationServiceV2.operations.CreateOrganization;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateOrganization {
	// *******************
	// Test Class Fields
	// *******************
	// Defining global variables
	private String environment = "";

	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		
	}

	@Test( description = "Create an IATA agency via API", 
		   groups = { "regression", "smoke", "passport", "agencyManagement", "api" })
	public void createOrganization_IATA() {
		CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
		createOrganization.setProgramType("IATA Number");
		createOrganization.sendRequest();
		TestReporter.logStep("Response Validation");		
		TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
	}
	
	@Test( description = "Create an Pseudo Number agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createOrganization_PseudoNumber() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("Pseudo IATA/CLIA Number");
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an CLIA agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createOrganization_CLIA() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("CLIA Number");
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an ABTA agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
				)
		public void createOrganization_ABTA() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("ABTA Number");
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an ARC agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createOrganization_ARC() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("ARC Number");
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}

	@Test( description = "Create an Group Code agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createOrganization_GroupCode() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("Wholesale Group Code");
			createOrganization.setProgramId(Randomness.randomNumber(8));
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an Chain Code agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createOrganization_ChainCode() {
			CreateOrganization createOrganization = new CreateOrganization(environment, "Main");
			createOrganization.setProgramType("Chain Code");
			createOrganization.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createOrganization.validateResponse( "Main_Response"), "Response Validation Result");		
		}

	
}
