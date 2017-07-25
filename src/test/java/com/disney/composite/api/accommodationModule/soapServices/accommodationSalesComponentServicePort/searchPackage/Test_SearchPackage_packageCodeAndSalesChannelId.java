package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_packageCodeAndSalesChannelId extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage" })
    public void testSearchPackage_packageCodeAndSalesChannelId() {

        SearchPackage search = new SearchPackage(environment, "Main");
        search.setPackageCode("X697A");
        search.setSalesChannelIDs("1");
        search.sendRequest();
        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", search);

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            SearchPackage clone = (SearchPackage) search.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(search, true), "Validating Response Comparison");
        }
    }
}
