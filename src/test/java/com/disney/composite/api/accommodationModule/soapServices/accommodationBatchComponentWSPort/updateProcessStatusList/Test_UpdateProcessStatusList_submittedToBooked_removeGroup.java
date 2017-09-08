package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageRemoveGroupTransactional;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_submittedToBooked_removeGroup extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_removeGroup() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        StageRemoveGroupTransactional remove = new StageRemoveGroupTransactional(Environment.getBaseEnvironmentName(environment), "Main");

        remove.setProcessName("REMOVEGROUP");
        remove.setTcg(getBook().getTravelComponentGroupingId());
        remove.sendRequest();
        TestReporter.logAPI(!remove.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", remove);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(remove.getResponseProcessId()));
        update.setProcessType("REMOVEGROUP");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        helper.validationOverall(helper.retrieveProcRunId(remove.getResponseProcessId()), "SUBMITTED", Randomness.generateCurrentDatetime().substring(0, 10));
        helper.validationRemoveGroup(helper.retrieveProcRunId(remove.getResponseProcessId()), getBook().getTravelComponentGroupingId());
    }

}
