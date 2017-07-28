package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForCancel;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForCancel;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.TestReporter;

public class TestGetStagedRecordsForCancel_NullProcessDataId {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWSPort", "getStagedRecordsForCancel" })
    @Parameters("environment")
    public void testGetStagedRecordsForCancel_NullProcessDataId(String environment) {

        GetStagedRecordsForCancel getStaged = new GetStagedRecordsForCancel(environment, "Main");
        getStaged.setProcessDataId(BaseSoapCommands.REMOVE_NODE.toString());
        getStaged.sendRequest();
        TestReporter.logAPI(!getStaged.getResponseStatusCode().equals("200"), "An error occurred getting staged records for cancel.", getStaged);

        try {
            getStaged.getReturn();
            TestReporter.assertTrue(false, "Return node found -- Response should contain nothing");
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(true, "Validating nothing is returned in the reponse for getStagedRecordsForCancel");
        }

    }
}
