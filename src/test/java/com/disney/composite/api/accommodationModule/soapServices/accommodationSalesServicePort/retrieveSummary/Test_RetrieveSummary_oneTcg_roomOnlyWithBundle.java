package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.AddBundleHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyWithBundle extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyWithBundle() {

        AddBundleHelper helper = new AddBundleHelper(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        helper.addBundle(getBook().getTravelPlanId(), getDaysOut());

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

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
