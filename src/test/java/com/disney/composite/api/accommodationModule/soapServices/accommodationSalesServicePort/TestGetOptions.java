package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptions;
import com.disney.utils.TestReporter;

public class TestGetOptions {
    private String environment = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions", "example" })
    public void testGetOptions_MainFlow() {
        TestReporter.logScenario("Test Get Options");
        GetOptions GetOptions = new GetOptions(environment, "Main");
        GetOptions.sendRequest();
        TestReporter.logAPI(!GetOptions.getResponseStatusCode().equals("200"), "An error occurred getting options", GetOptions);
        TestReporter.assertNotNull(GetOptions.getoptionKey(), "The response contains a option Key");
        TestReporter.assertNotNull(GetOptions.getoptionValue(), "The response contains a option Value");
    }
}
