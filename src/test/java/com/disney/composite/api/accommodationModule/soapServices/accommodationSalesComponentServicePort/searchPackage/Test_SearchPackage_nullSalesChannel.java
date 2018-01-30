package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_nullSalesChannel extends AccommodationBaseTest {

    private String pkg;
    private String desc;
    private String pkgCode;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage" })
    public void testSearchPackage_nullSalesChannel() {
        pkgCode = getPackageCode();
        SearchPackage search = new SearchPackage(environment, "Main");
        search.setBookingDate(Randomness.generateCurrentXMLDate());
        search.setPackageDescription("R Room Only");
        search.setPackageCode(pkgCode);
        search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
        search.setSalesChannelIDs(" ");
        search.sendRequest();
        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", search);

    }
}
