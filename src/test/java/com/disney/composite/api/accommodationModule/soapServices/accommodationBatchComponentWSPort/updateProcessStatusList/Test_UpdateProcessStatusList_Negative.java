package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessType() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);
        String faultString = "Invalid UpdateProcessStatus Request : Invalid Request";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList("20269510");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.INVALID_UPDATE_PROCESS_STATUS_RQ);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessingStatus() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);
        String faultString = "Invalid UpdateProcessStatus Request : Invalid Request";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList("20269510");
        update.setProcessType("MASS_CANCEL");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.INVALID_UPDATE_PROCESS_STATUS_RQ);

    }

}
