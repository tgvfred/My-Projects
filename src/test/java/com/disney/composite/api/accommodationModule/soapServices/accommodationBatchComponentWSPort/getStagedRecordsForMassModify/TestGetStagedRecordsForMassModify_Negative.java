package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestGetStagedRecordsForMassModify_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation", "negative" })
    public void testGetStagedRecordsForMassModify_invalidProcessDataId() {

        GetStagedRecordsForMassModify get = new GetStagedRecordsForMassModify(environment);

        String faultString = "Unexpected Error occurred";

        get.setProcessDataId("-1");
        get.sendRequest();

        TestReporter.assertTrue(get.getFaultString().contains(faultString), "Verify that the fault string [" + get.getFaultString() + "] contains [" + faultString + "].");
        validateApplicationError(get, LiloSystemErrorCode.UNEXPECTED_ERROR);

    }

    @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation", "negative" })
    public void testGetStagedRecordsForMassModify_nullProcessDataId() {

        GetStagedRecordsForMassModify get = new GetStagedRecordsForMassModify(environment);

        String faultString = "Unexpected Error occurred";

        get.setProcessDataId(BaseSoapCommands.REMOVE_NODE.toString());
        get.sendRequest();

        TestReporter.assertTrue(get.getFaultString().contains(faultString), "Verify that the fault string [" + get.getFaultString() + "] contains [" + faultString + "].");
        validateApplicationError(get, LiloSystemErrorCode.UNEXPECTED_ERROR);

    }

}
