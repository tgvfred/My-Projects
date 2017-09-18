package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CalculateUnsharedRatesHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_OneSharedRoomDetails extends AccommodationBaseTest {

    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_OneSharedRoomDetails() {

        calculate = new CalculateUnsharedRates(environment, "Main_2");
        calculate.setUnsharedChainSharedRoomDetailTCGId("0");
        calculate.setUnsharedChainSharedRoomDetailTCId("0");
        calculate.setUnsharedChainShareRoomDetailsTPSId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCGId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationTPSId("0");
        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates.", calculate);

        validations();

        calculate.sendRequest();
        if (Environment.isSpecialEnvironment(environment)) {
            CalculateUnsharedRates clone = (CalculateUnsharedRates) calculate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(calculate, true), "Validating Response Comparison");

        }

    }

    public void validations() {

        CalculateUnsharedRatesHelper helper = new CalculateUnsharedRatesHelper(getEnvironment());

        helper.validateUnsharedRoomRateDetailsResponse();
        helper.validateSharedRoomRateDetailsResponse();
        helper.validateSharedRoomRateDetailsResponseDiffers();
        helper.validateUnSharedRoomRateDetailsResponseDiffers();

    }

}
