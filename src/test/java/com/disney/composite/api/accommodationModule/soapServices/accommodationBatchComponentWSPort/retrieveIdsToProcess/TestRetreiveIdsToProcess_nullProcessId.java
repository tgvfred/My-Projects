package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.retrieveIdsToProcess;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.RetreiveIdsToProcess;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationBatchErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.TestReporter;

public class TestRetreiveIdsToProcess_nullProcessId extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
    }

    @Test(groups = { "api", "regression", "retreiveIdsToProcess", "accommodation", "accommodationBatchComponentWSPort" })
    public void Test_RetreiveIdsToProcess_nullProcessId() {
        String faultString = "Invalid Request : Process Data Id Required";

        RetreiveIdsToProcess retreiveIds = new RetreiveIdsToProcess(environment, "Main");
        retreiveIds.setProcessId(BaseSoapCommands.REMOVE_NODE.toString());
        retreiveIds.sendRequest();

        TestReporter.assertTrue(retreiveIds.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retreiveIds.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retreiveIds, AccommodationBatchErrorCode.INVALID_REQUEST);

        TestReporter.logStep("Response node return validation count.");
        try {
            retreiveIds.getProcessDataIdList();
            TestReporter.assertTrue(false, "Return node found -- Response should contain nothing");
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(true,
                    "Validating no ProcessDataIdList returned in the reponse for getRetreiveIdsToProcessResponse");
        }
        // Shouldn't be needed since the old environment will bring back different negative faults than the new environment
        // if (Environment.isSpecialEnvironment(environment)) {
        // RetreiveIdsToProcess clone = (RetreiveIdsToProcess) retreiveIds.clone();
        // clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
        // clone.sendRequest();
        // if (!clone.getResponseStatusCode().equals("200")) {
        // TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
        // }
        // clone.addExcludedBaselineAttributeValidations("@xsi:nil");
        // clone.addExcludedBaselineAttributeValidations("@xsi:type");
        // TestReporter.assertTrue(clone.validateResponseNodeQuantity(retreiveIds, true), "Validating Response Comparison");
        // }
    }

}
