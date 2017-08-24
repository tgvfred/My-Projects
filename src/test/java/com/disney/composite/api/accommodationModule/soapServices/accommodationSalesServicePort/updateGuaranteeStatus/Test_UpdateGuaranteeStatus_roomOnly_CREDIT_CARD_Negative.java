package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateGuaranteeStatusHelper;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_roomOnly_CREDIT_CARD_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_roomOnly_CREDIT_CARD_Negative() {

        String faultString = " Guarantee status can not be changed  : Guarantee status can not be changed";

        UpdateGuaranteeStatusHelper helper = new UpdateGuaranteeStatusHelper(environment);

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.GUARANTEE_STATUS_CANNOT_CHANGE);

    }

}
