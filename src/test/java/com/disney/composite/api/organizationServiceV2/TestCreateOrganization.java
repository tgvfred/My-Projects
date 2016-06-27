package com.disney.composite.api.organizationServiceV2;

import org.testng.annotations.BeforeMethod;
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

	/*
	 * Define a method that will be executed prior to each @Test method.
	 * The @BeforeTest argument "inheritGroups = true" ensures that the groups
	 * defined by the @Test method are also used by this method. The @Parameter
	 * aregument defines a list of expected parameters that are to be passed
	 * from a TestNG XML. The method arguments correspond to the number of
	 * arguments passed to the @Parameters line. This method sets local copies
	 * of the passed arguments and sets the virtual table path for the page
	 * classes to be used
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		
	}

	/*
	 * Define a method that will be executed after each @Test method.
	 * The @AfterTest argument "inheritGroups = true" ensures that the groups
	 * defined by the @Test method are also used by this method. The method
	 * argument is a TestNG object and contains information about the current
	 * test, such as the test name.
	 */
	/*@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {
		WebDriver driver = drivers.get(testName);
		driver.quit();
	}*/

	/*
	 * Define a method that contains the functionality under test NOTE:
	 * The @Test annotation signifies a method that contains within a
	 * self-contained, complete test. Multiple "@Test" methods may be called in
	 * a single Test Class.
	 * 
	 * NOTE: The data provider name must match the name of a data provider
	 * included in this Test Class
	 * 
	 * NOTE: The "groups ={ }" is a collection of strings, intended to be used
	 * for gathering tests of a specific type and running then together, without
	 * having to explicitly maintain a list
	 * 
	 * NOTE: The number of data points passed by the data provider must match
	 * the number of method arguments
	 */
	@Test( description = "Create an IATA agency via API", 
		   groups = { "regression", "smoke", "passport", "agencyManagement", "api" })
	public void createAgency_IATA() {
		CreateOrganization createAgency = new CreateOrganization(environment, "Main");
		createAgency.setProgramType("IATA Number");
		createAgency.sendRequest();
		TestReporter.logStep("Response Validation");		
		TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
	}
	
	@Test( description = "Create an Pseudo Number agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createAgency_PseudoNumber() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("Pseudo IATA/CLIA Number");
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an CLIA agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createAgency_CLIA() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("CLIA Number");
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an ABTA agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
				)
		public void createAgency_ABTA() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("ABTA Number");
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an ARC agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createAgency_ARC() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("ARC Number");
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}

	@Test( description = "Create an Group Code agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createAgency_GroupCode() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("Wholesale Group Code");
			createAgency.setProgramId(Randomness.randomNumber(8));
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}


	@Test( description = "Create an Chain Code agency via API", 
			   groups = { "regression", "smoke", "passport", "agencyManagement", "api" }
			)
		public void createAgency_ChainCode() {
			CreateOrganization createAgency = new CreateOrganization(environment, "Main");
			createAgency.setProgramType("Chain Code");
			createAgency.sendRequest();
			TestReporter.logStep("Response Validation");		
			TestReporter.assertTrue(createAgency.validateResponse( "Main_Response"), "Response Validation Result");		
		}

	
}
