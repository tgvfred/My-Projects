package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageCancelData;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessType() {
        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);
        StageCancelData cancel = new StageCancelData(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelContactName("Cancel Name");
        cancel.setCancelDate("2017-17-07");
        cancel.setCancelReasonCode("AIR");
        cancel.setIsOverridden("false");
        cancel.setIsWaived("false");
        cancel.setOVerridenCancelFEe("0");
        cancel.setTCg(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", cancel);
        String faultString = "Invalid UpdateProcessStatus Request : Invalid Request";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(cancel.getResponseProcessId()));
        update.setProcessingStatus("BOOKED");
        update.sendRequest();

        TestReporter.assertTrue(faultString.contains(update.getFaultString()), "Verify that the fault string [" + update.getFaultString() + "] contains that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.INVALID_UPDATE_PROCESS_STATUS_RQ);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessingStatus() {
        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);
        StageCancelData cancel = new StageCancelData(Environment.getBaseEnvironmentName(environment), "Main");
        cancel.setCancelContactName("Cancel Name");
        cancel.setCancelDate("2017-17-07");
        cancel.setCancelReasonCode("AIR");
        cancel.setIsOverridden("false");
        cancel.setIsWaived("false");
        cancel.setOVerridenCancelFEe("0");
        cancel.setTCg(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", cancel);
        String faultString = "Invalid UpdateProcessStatus Request : Invalid Request";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(cancel.getResponseProcessId()));
        update.setProcessType("MASS_CANCEL");
        update.sendRequest();

        TestReporter.assertTrue(faultString.contains(update.getFaultString()), "Verify that the fault string [" + update.getFaultString() + "] contains that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.INVALID_UPDATE_PROCESS_STATUS_RQ);
    }
}