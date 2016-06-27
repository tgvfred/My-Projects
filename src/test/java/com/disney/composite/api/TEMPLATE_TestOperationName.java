package com.disney.composite.api;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

@SuppressWarnings("unused")
public class TEMPLATE_TestOperationName {
	private String environment = "";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 *
	@Test(groups={"api", "regression", "serviceClusterName", "serviceName", "operationName"})
	public void testOperationName_DataScenario(){
		OperationName operationName = new OperationName(environment, "DataScenario" );
		// If no pre-reqs required, send request
		operationName.sendRequest();
		
		//Validate response error code equals 200
		TestReporter.assertEquals(operationName.getResponseStatusCode(), "200", "The response code was not 200");
		
		//Validate response contained form of expected data( not always applicable). Can use more than one validation point
		TestReporter.assertNotNull(operationName.getTravelPlanId(), "The response contains a Travel Plan ID");
	}
	
	 *
	 * This is an example of actual usage
	 * 
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "quickbook"})
	public void testQuickbook_MainFlow(){
		Quickbook quickbook = new Quickbook(environment, "Main" );
		quickbook.sendRequest();
		TestReporter.assertEquals(quickbook.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(quickbook.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(quickbook.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}
	
	*/
}
