package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyOneAccommodationsSummaryDetailsReturned extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyOneAccommodationsSummaryDetailsReturned() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify one AccommodationsSummaryDetails node is found.");
        TestReporter.assertTrue(retrieve.getAccommodationsSummaryDetails() <= 1, "Only one AccommodationsSummaryDetails node found! ");

    }

}
