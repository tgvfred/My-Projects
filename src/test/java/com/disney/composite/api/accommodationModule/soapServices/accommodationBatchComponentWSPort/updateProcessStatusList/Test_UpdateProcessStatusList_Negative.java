package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessType() {

        String faultString = "Unexpected Error occurred : updateProcessStatusList : java.lang.NullPointerException";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList("20269510");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, LiloSystemErrorCode.UNEXPECTED_ERROR);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullProcessingStatus() {

        String faultString = "Unexpected Error occurred : updateProcessStatusList : java.lang.NullPointerException";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList("20269510");
        update.setProcessType("MASS_CANCEL");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, LiloSystemErrorCode.UNEXPECTED_ERROR);

    }

    // May not be valid
    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList", "negative" })
    public void testUpdateProcessStatusList_nullTpsId_roomingList() {

        String faultString = "Unexpected Error occurred : updateProcessStatusList : java.lang.NullPointerException";

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList("20274595");
        update.setProcessType("ROOMINGLIST");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, LiloSystemErrorCode.UNEXPECTED_ERROR);

    }
}
