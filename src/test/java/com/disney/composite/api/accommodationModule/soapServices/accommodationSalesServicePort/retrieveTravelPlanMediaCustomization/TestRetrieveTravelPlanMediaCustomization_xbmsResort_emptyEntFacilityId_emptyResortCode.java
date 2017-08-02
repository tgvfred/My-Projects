package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveTravelPlanMediaCustomization;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTravelPlanMediaCustomization;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.ResortInfo;
import com.disney.utils.dataFactory.ResortInfo.ResortColumns;

public class TestRetrieveTravelPlanMediaCustomization_xbmsResort_emptyEntFacilityId_emptyResortCode extends AccommodationBaseTest {
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveTravelPlanMediaCustomization"})
	public void testRetrieveTravelPlanMediaCustomization_xbmsResort_emptyEntFacilityId_emptyResortCode(){
		String travelPlanId = getBook().getTravelPlanId();
		String locationId = getLocationId();
		String expectedFacilityEligibility = "false";
					
		RetrieveTravelPlanMediaCustomization retrieve = new RetrieveTravelPlanMediaCustomization(environment, "main");
		retrieve.setEnterpriseFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setTravelPlanId(travelPlanId);
		retrieve.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setLocationId(locationId);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"),
				"Error was returned: " + retrieve.getFaultString(), retrieve);
		
		// Validate old vs. new service
		if (Environment.isSpecialEnvironment(getEnvironment())) {
			RetrieveTravelPlanMediaCustomization clone = (RetrieveTravelPlanMediaCustomization) retrieve.clone();
			clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
			int tries = 0;
			int maxTries = 40;
			boolean success = false;
			tries = 0;
			maxTries = 40;
			success = false;
			do {
				Sleeper.sleep(500);
				clone.sendRequest();
				if (retrieve.getResponseStatusCode().equals("200")) {
					success = true;
				} else {
					tries++;
				}
			} while (tries < maxTries && !success);
			if (!clone.getResponseStatusCode().equals("200")) {
				TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
						"Error was returned: " + clone.getFaultString(), clone);
			}

			clone.addExcludedBaselineXpathValidations("/Envelope/Header");
			clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveTravelPlanMediaCustomization/travelPlanId");
			clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveTravelPlanMediaCustomization/resortCode");
			clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveTravelPlanMediaCustomization/locationId");
			clone.addExcludedXpathValidations(
					"/Envelope/Body/retrieveTravelPlanMediaCustomization/response/isFacilityEligible"); 
			TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true), "Validating Response Comparison");
		}
		
		//validate that response xml is true
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "Error occurred when getting response code", retrieve);
		TestReporter.assertTrue(retrieve.getIsFacilityEligible().equals(expectedFacilityEligibility), "Verify that the isFacilityEligible [" + retrieve.getIsFacilityEligible() + "] matches the expected value [" + expectedFacilityEligibility + "]");
		
		//validate that there are no media customization nodes in the response xml
		try {retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomizationResponse/response/mediaCustomization");} catch (XPathNotFoundException e) {TestReporter.assertTrue(true, "No media customization nodes returned.");}
		}
	}
			
	