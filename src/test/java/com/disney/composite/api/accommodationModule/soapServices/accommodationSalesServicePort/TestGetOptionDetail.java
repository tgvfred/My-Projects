package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail {
    private String environment = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "GetOptionDetail", "example" })
    public void testGetOptionDetail_MainFlow() {
        TestReporter.logScenario("Test Get Option Details");
        GetOptionDetail GetOptionDetail = new GetOptionDetail(environment, "Main");
        GetOptionDetail.sendRequest();
        TestReporter.logAPI(!GetOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details", GetOptionDetail);
    }
}
