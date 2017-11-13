package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_Negative extends AccommodationBaseTest {

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_invalidTcg() {

        String faultString = "Accommodations not found : ACCOMMODATIONS NOT FOUND";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId("4815162342");
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_negativeTcg() {

        String faultString = "Required parameters are missing : Invalid Request";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId("-1");
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_nullRequest() {

        String faultString = "Required parameters are missing : Invalid Request";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/travelComponentGroupingId", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/guaranteedByEnum", BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_nullTcg() {

        String faultString = "Required parameters are missing : Invalid Request";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/travelComponentGroupingId", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_nullGuaranteedByEnum() {

        String faultString = "Required parameters are missing : Invalid Request";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateGuaranteeStatus/request/guaranteedByEnum", BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_cancelled() {

        String faultString = " Guarantee status can not be changed  : Guarantee status can not be changed on shared room ";

        cancel();
        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.GAURANTEE_STATUS_CAN_NOT_BE_CHANGED);

    }

}
