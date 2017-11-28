package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_nullSharedRoomDetails extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules", "negative" })
    public void testValidateShareRules_nullSharedRoomDetails() {

        String faultString = "Required parameters are missing : null";

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setSharedRoomDetails(BaseSoapCommands.REMOVE_NODE.toString());
        validate.sendRequest();

        TestReporter.assertTrue(validate.getFaultString().contains(faultString), "Verify that the fault string [" + validate.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(validate, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }
}
