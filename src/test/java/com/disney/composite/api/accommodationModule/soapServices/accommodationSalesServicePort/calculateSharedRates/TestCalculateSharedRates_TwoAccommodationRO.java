package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateSharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class TestCalculateSharedRates_TwoAccommodationRO extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates", "negative" })
    public void testCalculateSharedRates_TwoAccommodationRO() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "Main");

        calculate.sendRequest();

    }
}
