package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class TestOverrideAccommodationRates_extRefWithNullTCG extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

}
