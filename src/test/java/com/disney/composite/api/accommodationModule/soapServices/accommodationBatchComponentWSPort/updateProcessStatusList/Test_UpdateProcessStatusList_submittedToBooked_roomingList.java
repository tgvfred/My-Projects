package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_submittedToBooked_roomingList extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_roomingList() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        // update.setProcessDataIdList(helper.retrieveProcRunId(remove.getResponseProcessId()));
        update.setProcessType("");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        // helper.validationOverall(helper.retrieveProcRunId(remove.getResponseProcessId()), "BOOKED");
        // helper.validationRoomList(helper.retrieveProcRunId(remove.getResponseProcessId()));
    }
}
