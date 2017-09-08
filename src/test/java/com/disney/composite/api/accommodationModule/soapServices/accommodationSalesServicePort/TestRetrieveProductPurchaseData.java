package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveProductPurchaseData;
import com.disney.utils.TestReporter;

public class TestRetrieveProductPurchaseData {
    private String environment = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveProductPurchaseData", "example" })
    public void testRetrieveProductPurchaseData_MainFlow() {
        TestReporter.logScenario("Test Retrieve Product Purchase Data");
        RetrieveProductPurchaseData RetrieveProductPurchaseData = new RetrieveProductPurchaseData(environment, "Main");
        RetrieveProductPurchaseData.sendRequest();
        TestReporter.logAPI(!RetrieveProductPurchaseData.getResponseStatusCode().equals("200"), "An error occurred retrieving product purchase data", RetrieveProductPurchaseData);
        TestReporter.assertNotNull(RetrieveProductPurchaseData.getproductId(), "The response contains a Product Id");
        TestReporter.assertNotNull(RetrieveProductPurchaseData.getproductTypeName(), "The response contains a Product Type Name");
    }
}