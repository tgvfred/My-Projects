package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_packageCodeOnly extends AccommodationBaseTest {

    private String pkg;
    private String desc;
    private String pkgCode;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage" })
    public void testSearchPackage_packageCodeOnly() {
        pkgCode = getPackageCode();
        SearchPackage search = new SearchPackage(environment, "Main");
        search.setPackageCode(pkgCode);
        // if (Environment.isSpecialEnvironment(environment)) {
        // search.setSalesChannelIDs("40748164");
        // }
        search.setPackageDescription(BaseSoapCommands.REMOVE_NODE.toString());
        search.setBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.setSalesChannelIDs(BaseSoapCommands.REMOVE_NODE.toString());
        search.setResortArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();
        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", search);

        search.getPackageDescriptionByPackageCode(pkgCode);

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            SearchPackage clone = (SearchPackage) search.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(search, true), "Validating Response Comparison");
        }
    }

}
