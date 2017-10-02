package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForCancel;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForCancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationBatchErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestGetStagedRecordsForCancel_NullProcessDataId extends BaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWSPort", "getStagedRecordsForCancel", "negative" })
    @Parameters("environment")
    public void testGetStagedRecordsForCancel_NullProcessDataId(String environment) {

        GetStagedRecordsForCancel getStaged = new GetStagedRecordsForCancel(environment, "Main");
        getStaged.setProcessDataId(BaseSoapCommands.REMOVE_NODE.toString());
        getStaged.sendRequest();
        String faultString = "Invalid Request : Invalid Request";
        TestReporter.assertEquals(getStaged.getFaultString(), faultString, "Verify that the fault string [" + getStaged.getFaultString() + "] is that which is expected [" + faultString + "].");

        validateApplicationError(getStaged, AccommodationBatchErrorCode.INVALID_REQUEST);
    }
}
