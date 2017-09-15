package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestOverrideAccommodationRates_RO_ThreeAdultsOneNight extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "overrideAccommodationRates" })
    public void testOverrideAccommodationRates_RO_ThreeAdultsOneNight(String key, String value) {
        // System.out.println(key);
        // System.out.println(value);

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("ROLE");

        getOptionDetail.setOptionKeyVal(key.split(",")[0]);

        getOptionDetail.sendRequest();
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getOptionDetail.getFaultString(), getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(value.split(",")[0]), "The response Option VALUE [" + getOptionDetail.getOptionValue() + "] matches the PartyService getOptions value [" + value.split(",")[0] + "].");
        TestReporter.assertAll();

    }

}
