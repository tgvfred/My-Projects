package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionsByFilter;
import com.disney.utils.TestReporter;

public class TestGetOptionsFilter {
    private String environment = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "GetOptionsByFilter", "example" })
    public void testGetOptionsByFilterByFilter_Role() {
        TestReporter.logScenario("Test Get Options By Filter - ROLE");
        GetOptionsByFilter GetOptionsByFilter = new GetOptionsByFilter(environment, "getOptionsByFilter_Role");
        GetOptionsByFilter.sendRequest();
        TestReporter.logAPI(!GetOptionsByFilter.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", GetOptionsByFilter);
        TestReporter.assertNotNull(GetOptionsByFilter.getoptionKey(), "The response contains a option Key");
        TestReporter.assertNotNull(GetOptionsByFilter.getoptionValue(), "The response contains a option Value");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "GetOptionsByFilter", "example" })
    public void testGetOptionsByFilterByFilter_LANGUAGE() {
        TestReporter.logScenario("Test Get Options By Filter - LANGUAGE");
        GetOptionsByFilter GetOptionsByFilter = new GetOptionsByFilter(environment, "getOptionsByFilter_LANGUAGE");
        GetOptionsByFilter.sendRequest();
        TestReporter.logAPI(!GetOptionsByFilter.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", GetOptionsByFilter);
        TestReporter.assertNotNull(GetOptionsByFilter.getoptionKey(), "The response contains a option Key");
        TestReporter.assertNotNull(GetOptionsByFilter.getoptionValue(), "The response contains a option Value");
    }
}
