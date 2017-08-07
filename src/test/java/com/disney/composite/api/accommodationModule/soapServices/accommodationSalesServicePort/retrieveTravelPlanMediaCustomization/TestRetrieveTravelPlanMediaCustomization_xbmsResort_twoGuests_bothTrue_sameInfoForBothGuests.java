package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveTravelPlanMediaCustomization;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTravelPlanMediaCustomization;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieveTravelPlanMediaCustomization_xbmsResort_twoGuests_bothTrue_sameInfoForBothGuests extends AccommodationBaseTest {
	
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveTravelPlanMediaCustomization"})
	public void TestRetrieveTravelPlanMediaCustomization_xbmsResort_twoGuests_bothTrue_sameInfoForBothGuests(){
		String facilityId = getFacilityId();
		String guestInfo = getPartyId();
		String resortCode = "1U";
		String locationId = getLocationId();
		String party1Id = guestInfo;
		String party1Type = "true";
		String party2Id = guestInfo;
		String party2Type = "true";
		String expectedFacilityEligibility = "false";
				
		RetrieveTravelPlanMediaCustomization retrieve = new RetrieveTravelPlanMediaCustomization(environment, "main");
		retrieve.setEnterpriseFacilityId(facilityId);
		retrieve.setTravelPlanId(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setResortCode(resortCode);
		retrieve.setLocationId(locationId);
		retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party1Id", party1Id);
		retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party1Type", party1Type);
		retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party2Id", party2Id);
		retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomization/serviceContext/party2Type", party2Type);
		retrieve.sendRequest();
		
		//Validate old vs. new service
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
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
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
		TestReporter.assertEquals(retrieve.getIsFacilityEligible(), expectedFacilityEligibility, "Verify that the isFacilityEligible [" + retrieve.getIsFacilityEligible() + "] matches the expected value [" + expectedFacilityEligibility + "]");
				
		//validate that there are no media customization nodes in the response xml
		try {retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveTravelPlanMediaCustomizationResponse/response/experienceMediaDetails");} catch (XPathNotFoundException e) {TestReporter.assertTrue(true, "No media customization nodes returned.");}
		}
	}
