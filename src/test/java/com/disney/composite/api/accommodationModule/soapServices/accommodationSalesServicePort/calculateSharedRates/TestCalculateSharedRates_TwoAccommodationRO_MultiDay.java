package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateSharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates_TwoAccommodationRO_MultiDay extends AccommodationBaseTest {

    // @Override
    // @BeforeMethod(alwaysRun = true)
    // @Parameters("environment")
    // public void setup(String environment) {
    // setEnvironment(environment);
    // isComo.set("false");
    // setDaysOut(0);
    // setNights(1);
    // setArrivalDate(getDaysOut());
    // setDepartureDate(getDaysOut() + getNights());
    // setValues(getEnvironment());
    // isComo.set("false");
    // bookReservation();
    //
    // }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates" })
    public void testCalculateSharedRates_TwoAccommodationRO() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "TwoAccommodations");

        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", calculate);

        TestReporter.softAssertTrue(calculate.getShared().equals("true"), "The Shared node is set to [" + calculate.getShared() + "].");

        if (Environment.isSpecialEnvironment(environment)) {
            CalculateSharedRates clone = (CalculateSharedRates) calculate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(calculate, true), "Validating Response Comparison");

        }
    }
}