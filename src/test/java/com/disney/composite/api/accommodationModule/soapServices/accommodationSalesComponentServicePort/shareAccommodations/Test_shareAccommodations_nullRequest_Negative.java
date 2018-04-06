package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.core.BaseSoapCommands;

public class Test_shareAccommodations_nullRequest_Negative extends BaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_nullRequest_Negative() {

        ShareAccommodations share = new ShareAccommodations(environment);

        share.setRequest(BaseSoapCommands.REMOVE_NODE.toString());
        share.sendRequest();
        validateApplicationError(share, MISSING_REQUIRED_PARAM_EXCEPTION);
    }
}
