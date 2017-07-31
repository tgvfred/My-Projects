package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassCancelTransactional;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_submittedToBooked_massCancel extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_massCancel() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        StageMassCancelTransactional cancel = new StageMassCancelTransactional(environment, "Main");
        cancel.setCancelContactName("Cancel Name");
        cancel.setCancelDate("2017-17-07");
        cancel.setCancelReasonCode("AIR");
        cancel.setIsOverridden("false");
        cancel.setIsWaived("false");
        cancel.setOVerridenCancelFEe("0");
        cancel.setTCg(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", cancel);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(cancel.getResponseProcessId()));
        update.setProcessType("MASS_CANCEL");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        helper.validationOverall(helper.retrieveProcRunId(cancel.getResponseProcessId()), "BOOKED");

        helper.validationMassCancel(helper.retrieveProcRunId(cancel.getResponseProcessId()), getBook().getTravelPlanId(), getBook().getTravelComponentGroupingId());

    }
}
