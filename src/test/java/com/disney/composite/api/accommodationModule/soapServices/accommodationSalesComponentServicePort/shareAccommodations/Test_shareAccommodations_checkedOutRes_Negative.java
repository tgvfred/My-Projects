package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.core.BaseSoapCommands;

public class Test_shareAccommodations_checkedOutRes_Negative extends BaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_checkedOutRes_Negative() {

        ShareAccommodations share = new ShareAccommodations(environment);

        share.setAccommodation(BaseSoapCommands.REMOVE_NODE.toString());

        share.sendRequest();
        validateApplicationError(share, ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED);

    }
}