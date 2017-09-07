package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class TestGetOptionDetail_SALUTATION extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" })
    public void testGetOptionDetail_SALUATION() {

    }
}
