package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_twoSharedRoomDeatils_noOverlap extends AccommodationBaseTest {

    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_twoSharedRoomDeatils_noOverlap() {

        calculate = new CalculateUnsharedRates(environment, "NoOverlap");
        calculate.sendRequest();
        System.out.print(calculate.getRequest());
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates.", calculate);

        validateNumberShareChainsResponseNodes();

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

    public void validateNumberShareChainsResponseNodes() {
        TestReporter.logStep("Validate the number of shareChains response nodes");
        // Verify that 2 response nodes were returned for the share chains node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains"), 2, "Verify that there were two response nodes returned for the ShareChain node.");
        TestReporter.assertAll();
    }

    public void validateNumberShareRoomDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of shareRoomDetails response nodes");
        // Verify that 2 response nodes were returned for the share chains node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails"), 1, "Verify that there were two response nodes returned for the ShareChain node.");
        TestReporter.assertAll();
    }

    public void validateFirstShareChain() {
        String resortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/startDate");
        String resortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/endDate");
        String sharedRoomResortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/sharedRoomDetail/resortPeriod/startDate");
        String sharedRoomResortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/sharedRoomDetail/resortPeriod/endDate");

        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsStartDate(), resortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getShareChainUnSharedRoomDetailsStartDate() + "] that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsEndDate(), resortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getShareChainUnSharedRoomDetailsEndDate() + "] that which is expected [" + resortPeriodEndDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsStartDate(), sharedRoomResortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getShareChainUnSharedRoomDetailsStartDate() + "] that which is expected [" + sharedRoomResortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsEndDate(), sharedRoomResortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getShareChainUnSharedRoomDetailsEndDate() + "] that which is expected [" + sharedRoomResortPeriodEndDate + "].");

    }

}
