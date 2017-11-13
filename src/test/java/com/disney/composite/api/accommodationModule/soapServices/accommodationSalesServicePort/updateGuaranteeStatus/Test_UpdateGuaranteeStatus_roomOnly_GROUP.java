package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateGuaranteeStatusHelper;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_roomOnly_GROUP extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus" })
    public void testUpdateGuaranteeStatus_roomOnly_GROUP() {

        UpdateGuaranteeStatusHelper helper = new UpdateGuaranteeStatusHelper(environment);

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("GROUP");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        helper.validation(getBook().getTravelPlanId(), "DEPOSIT", "GROUP_GUARANTEED", "Y");

        // if (Environment.isSpecialEnvironment(environment)) {
        // // Validation when running in Latest_CM
        // helper.validation(getBook().getTravelPlanId(), "GROUP_GUARANTEED", "NONE", "Y");
        // } else {
        // // Validation when running in Latest
        // helper.validation(getBook().getTravelPlanId(), "DEPOSIT", "GROUP_GUARANTEED", "Y");
        //
        // }
    }

}
