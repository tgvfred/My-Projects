package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchWSPort.getStagedRecordsForReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForReinstate;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.RetrieveComment;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestGetStagedRecordsForReinstate_nullProcessDataId extends AccommodationBaseTest {

    private GetStagedRecordsForReinstate stageReinstate;

    @Test(groups = { "api", "regression", "getStagedRecordsForReinstate", "accommodation" })
    public void Test_GetStagedRecordsForReinstate_nullProcessDataId() {

        // get staged records for reinstate.
        stageReinstate = new GetStagedRecordsForReinstate(environment, "Main");
        stageReinstate.setRequestNodeValueByXPath("/Envelope/Body/getStagedRecordsForReinstate/processDataId", BaseSoapCommands.REMOVE_NODE.toString());
        stageReinstate.sendRequest();
        TestReporter.logAPI(!stageReinstate.getResponseStatusCode().equals("200"), "Verify that no error occurred getting staged records for reinstate: " + stageReinstate.getFaultString(), stageReinstate);
        validateResponseReturnNode();

        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveComment clone = (RetrieveComment) stageReinstate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(stageReinstate, true), "Validating Response Comparison");
        }

    }

    public void validateResponseReturnNode() {
        TestReporter.logStep("Validate the response node has one 'return' node.");
        int numExpectedNodes = 0;
        int returnNodes = stageReinstate.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForReinstateResponse/return");
        TestReporter.softAssertEquals(returnNodes, numExpectedNodes, "Verify that the response returns the number of 'return' nodes [" + returnNodes + "] is that which is expected [" + numExpectedNodes + "].");

    }
}
