package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyOneGuest_OneGuestReferences extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyOneGuest_OneGuestReferences() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify one GuestReferenceDetails node is found.");
        TestReporter.assertTrue(retrieve.getGuestReferenceDetails() > 1, "Only one GuestReferenceDetails node found! ");

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

    }

}
