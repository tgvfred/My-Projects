package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.retrieveIdsToProcess;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.RetreiveIdsToProcess;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestRetreiveIdsToProcess_nullProcessId extends AccommodationBaseTest {
	@Test(groups = { "api", "regression", "retreiveIdsToProcess", "accommodation", "accommodationBatchComponentWSPort" })
	public void Test_RetreiveIdsToProcess_nullProcessId() {
		String faultString = "Unexpected Error occurred : retreiveIdsToProcess : The given id must not be null! : The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!";
		
		RetreiveIdsToProcess retreiveIds = new RetreiveIdsToProcess(environment, "Main");
		retreiveIds.setProcessId(BaseSoapCommands.REMOVE_NODE.toString());
		retreiveIds.sendRequest();
		
		TestReporter.assertTrue(retreiveIds.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retreiveIds.getFaultString() + "] is that which is expected [" + faultString + "].");
		validateApplicationError(retreiveIds, AccommodationErrorCode.UNEXPECTED_ERROR_OCCURRED);
        
		TestReporter.logStep("Response node return validation count.");
		try {
			retreiveIds.getProcessDataIdList();
			TestReporter.assertTrue(false, "Return node found -- Response should contain nothing");
		} catch (XPathNotFoundException e) {
			TestReporter.assertTrue(true,
					"Validating no ProcessDataIdList returned in the reponse for getRetreiveIdsToProcessResponse");
		}
		
        if (Environment.isSpecialEnvironment(environment)) {
        	RetreiveIdsToProcess clone = (RetreiveIdsToProcess) retreiveIds.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retreiveIds, true), "Validating Response Comparison");
        }
    }
	
}


